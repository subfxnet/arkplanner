package net.subfx.arkplanner.js

import net.subfx.arkplanner.css.DocStyles

import scala.collection.immutable.SortedMap
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.document
import scalajs.js.JSApp
import scalacss.Defaults._
import scalacss.ScalaCssReact._

/**
 * Main entry point for Scala.js SPA
 */
object ArkPlanner extends JSApp {

  case class Survivor(level: Int, engrams: List[Engram]) {

    def spentPoints: Int = (engrams map(e => e.points)).sum

    def availablePoints: Int = (Engrams.levels filterKeys ((k: Int) => level >= k)).values.sum - spentPoints

    def hasEngram(e: Engram): Boolean = engrams contains e

    def toURI: String = "?" + level + "&" + ((engrams map (e => e.id)) mkString ",")
  }

  def loadSurvivor: Survivor = try {
    val args = document.location.search.tail.replace("/", "").split("&")
    Survivor(
      args.head.toInt,
      (args(1).split(",") flatMap { id =>
        Engrams.engramById(id.toInt)
      }).toList
    )
  } catch {
    case e: Exception => Survivor(1, Nil)
  }

  class Backend(s: BackendScope[Unit, Survivor]) {

    def setLevel(e: ReactEventI) = {
      val level = e.target.value.toInt
      s.modState(_ => Survivor(level, s.state.engrams.filter(e => e.reqLevel <= level)))
    }

    def checkLevel(e: Engram): Boolean = s.state.level >= e.reqLevel

    def checkPoints(e: Engram): Boolean = (s.state.availablePoints - e.points) >= 0

    def checkPrereqs(e: Engram): Boolean = e.preReqs forall (en => s.state.engrams contains en)

    def toggleEngram(id: Int) = {
      Engrams.table.find(en => en.id == id) foreach { selectedEngram =>
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

  val sortedTable = SortedMap(Engrams.table.groupBy(_.reqLevel).toArray:_*)

  val levelChoices = Engrams.levels.keys.toArray.sorted

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
          levelChoices map { k => <.option((s.level == k) ?= (^.selected := "selected"), k) }
        ),
        <.span(
          ^.`class` := "points",
          "Available Points: " + s.availablePoints
        ),
        <.span(
          ^.`class` := "points",
          "Spent Points: " + s.spentPoints
        ) /* ,
        <.span(
          ^.`class` := "uri",
          <.label(
            ^.`for` := "uri",
            "Survivor URI: "
          ),
          <.input(
            ^.id := "uri",
            ^.value := s.toURI
          )
        )*/
      )
    }).build

  val engramIcon = ReactComponentB[(Engram, Survivor, Backend)]("EngramIcon").render(
    P => {

      val (e, s, b) = P
      val name: String = if (e.name.length >= 30) e.name.substring(0, 30-3) + "..." else e.name

      <.div(
        DocStyles.engramBgImg(e),
        ^.`class` := "engram-icon",
        s.hasEngram(e) ?= (^.`class` := "engram-active"),
        ^.onClick --> b.toggleEngram(e.id),
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
    .initialState(loadSurvivor)
    .backend(new Backend(_))
    .render( (_, S, B) =>
      <.div(
        ^.`class` := "container",
        topBar((S, B)),
        engramTable((S, B))
      )
  ).buildU


  def main(): Unit = {
    DocStyles.addToDocument()
    React.render(Page(), document.body)
  }


}
