package net.subfx.arkplanner.js

import scala.collection.immutable.SortedMap
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.document
import scalajs.js.JSApp

/**
 * Main entry point for Scala.js SPA
 */
object ArkPlanner extends JSApp {

  import Engrams.{levels, table}

  case class Survivor(level: Int, engrams: List[Engram]) {

    def spentPoints: Int = (engrams map(e => e.points)).sum

    def availablePoints: Int = (levels filterKeys ((k: Int) => level >= k)).values.sum - spentPoints

    def hasEngram(e: Engram): Boolean = engrams contains e
  }

  val initialSurvivor = Survivor(1, Nil)

  class Backend(s: BackendScope[Unit, Survivor]) {

    def setLevel(e: ReactEventI) = {
      val level = e.target.value.toInt
      s.modState(_ => Survivor(level, s.state.engrams.filter(e => e.reqLevel <= level)))
    }

    def checkLevel(e: Engram): Boolean = s.state.level >= e.reqLevel

    def checkPoints(e: Engram): Boolean = (s.state.availablePoints - e.points) >= 0

    def checkPrereqs(e: Engram): Boolean = e.preReqs forall (en => s.state.engrams contains en)

    def toggleEngram(id: String) = {
      table.filter(en => mkId(en.name) == id) foreach { selectedEngram =>
        s.modState(_ => Survivor(s.state.level,
        if (s.state.hasEngram(selectedEngram))
          s.state.engrams.filterNot(en => en == selectedEngram)
        else
          if (checkPoints(selectedEngram) && checkPrereqs(selectedEngram) && checkLevel(selectedEngram))
            selectedEngram :: s.state.engrams
          else
            s.state.engrams
        ))
      }
    }

    def mkId(s: String) = s.replace(" ", "")
  }

  val sortedTable = SortedMap(table.groupBy(_.reqLevel).toArray:_*)

  val topBar = ReactComponentB[(Survivor, Backend)]("LevelSelect")
    .render( P => {
      val (s, b) = P
      <.div(
        ^.`class` := "top-bar",
        <.label(
          ^.`for` := "level",
          "Select Level: "
        ),
        <.select(
          ^.id := "level",
          ^.onChange ==> b.setLevel,
          sortedTable.keys map { k => <.option((s.level == k) ?= (^.selected := "true"), k) }
        ),
        <.span(
          ^.`class` := "points",
          "Available Points: " + s.availablePoints
        ),
        <.span(
          ^.`class` := "points",
          "Spent Points: " + s.spentPoints
        )
      )
    }).build

  val engramIcon = ReactComponentB[(Engram, Survivor, Backend)]("EngramIcon").render(
    P => {

      val (e, s, b) = P
      val name: String = if (e.name.length >= 30) e.name.substring(0, 30-3) + "..." else e.name
      val id: String = b.mkId(e.name)

      val bgStyleAttr = "bgImage".reactStyle
      val bgStyle = bgStyleAttr :=
        """
          |content: " ";
          |  display: block;
          |  position: absolute;
          |  left: 0;
          |  top: 0;
          |  width: 100%;
          |  height: 100%;
          |  z-index: 1;
          |  opacity: 0.4;
          |  background-image: url("http://hydra-media.cursecdn.com/ark.gamepedia.com/c/c4/Paintbrush.png");
          |  background-repeat: no-repeat;
          |  background-position: 50% 0;
          |  -ms-background-size: cover;
          |  -o-background-size: cover;
          |  -moz-background-size: cover;
          |  -webkit-background-size: cover;
          |  background-size: cover;
        """.stripMargin

      <.div(
        ^.`class` := "engram-icon",
        ^.id := id,
        //e.imageUrl != "" ?= bgStyle,
        s.hasEngram(e) ?= (^.`class` := "engram-active"),
        ^.onClick --> b.toggleEngram(id),
        <.table(
          <.tbody(
            <.tr(
              <.td(^.`class` := "engram-level", "L: " + e.reqLevel),
              <.td(^.`class` := "engram-points", "Ep: " + e.points)
            ),
            <.tr(
              <.td(
                ^.colSpan := "2", ^.title := e.name, ^.`class` := "engram-name", name)
            )
          )  // tbody
        )    // table
      )      // div
    }
  ).build

  val engramTable = ReactComponentB[(Survivor, Backend)]("EngramTable")
    .render( P => {
      val (s: Survivor, b: Backend) = P
      <.div(
        ^.`class` := "engrams",
        sortedTable map { case (level, engrams) =>
          engrams map (e => engramIcon((e, s, b)))
        }
      )}
  ).build

  val Page = ReactComponentB[Unit]("Page")
    .initialState(initialSurvivor)
    .backend(new Backend(_))
    .render( (_, S, B) =>
      <.div(
        ^.`class` := "container",
        topBar((S, B)),
        engramTable((S, B))
      )
  ).buildU


  def main(): Unit = React.render(Page(), document.body)

}
