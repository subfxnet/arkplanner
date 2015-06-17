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

    def availablePoints: Int = (Levels.levels filterKeys ((k: Int) => level >= k)).values.sum - spentPoints

    def hasEngram(e: Engram): Boolean = engrams contains e

    def toURI: String = document.location.origin + "/?" + level + "&" + ((engrams map (e => e.id)) mkString ",")
  }

  def loadSurvivor: Survivor = {
    if (document.location.search != "") {
      try {
        val args = document.location.search.tail.replace("/", "").split("&")
        Survivor(
          args.head.toInt,
          (args(1).split(",") flatMap { id =>
            Engrams.engramById(id.toInt)
          }).toList
        )
      } catch {
        case e: Exception => Survivor(65, Nil)
      }
    }
    else Survivor(65, Nil)
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
      Engrams.engramList.find(en => en.id == id) foreach { selectedEngram =>
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
  }

  val sortedTable = SortedMap(Engrams.engramList.groupBy(_.reqLevel).toArray:_*)

  val levelChoices = Levels.levels.keys.toArray.sorted

  val sideBar = ReactComponentB[(Survivor, Backend)]("SideBar")
    .render( P => {
      val (s, b) = P
      <.div(
        ^.`class` := "sidebar",
        <.div(
          ^.`class` := "blurb",
          "Ark: Survival Evolved survivor planner!"
        ),
        <.div(
          ^.`class` := "level-select",
          <.div("Choose your level:"),
          <.select(
            ^.id := "level",
            ^.autoComplete := "off",
            ^.value := s.level,
            ^.onChange ==> b.setLevel,
            levelChoices map { k => <.option((s.level == k) ?= (^.selected := "true"), k) }
          )
        ),
        <.div(
          ^.`class` := "points-outer",
          "Available Points",
          <.div(
            ^.`class` := "points",
            s.availablePoints
          )
        ),
        <.div(
          ^.`class` := "points-outer",
          "Spent Points",
          <.div(
            ^.`class` := "points",
            s.spentPoints
          )
        ),
        <.ul(
          <.li(
            <.div(
              ^.`class` := "key-container",
              <.div(^.`class` := "level-key", "5"),
              <.div(^.`class` := "key-label", "Level")
            )
          ),
          <.li(
            <.div(
              ^.`class` := "key-container",
              <.div(^.`class` := "ep-key", "3"),
              <.div(^.`class` := "key-label", "Engram Points")
            )
          ),
          <.li(
            <.div(
              ^.`class` := "key-container",
              <.div(^.`class` := "pre-rec-key", "P"),
              <.div(^.`class` := "key-label", "Prerequisites")
            )
          ),
          <.li(
            <.div(
              ^.`class` := "key-container",
              <.div(^.`class` := "pre-rec-key", "R"),
              <.div(^.`class` := "key-label", "Recipe (materials)")
            )
          )
        ),
        <.div(
          ^.`class` := "uri",
          <.label(
            ^.`for` := "uri",
            "Direct link to your survivor: "
          ),
          <.textarea(
            ^.id := "uri",
            ^.cols := 20,
            ^.rows := 5,
            ^.value := s.toURI
          )
        )
      )
    }).build

  val engramIcon = ReactComponentB[(Engram, Survivor, Backend)]("EngramIcon").render(
    P => {

      val (e, s, b) = P
      val name: String = if (e.name.length >= 30) e.name.substring(0, 30-3) + "..." else e.name

      <.div(
        DocStyles.engramBgImg(e),
        ^.`class` := "icon",
        s.hasEngram(e) ?= (^.`class` := "icon-active"),
        ^.onClick --> b.toggleEngram(e.id),
        <.div(
          ^.`class` := "icon-inner",
          <.div(^.`class` := "level", e.reqLevel),
          <.div(^.`class` := "ep", e.points)
        ),
        <.div(^.`class` := "name", e.name),
        <.div(^.`class` := "icon-inner",
          <.div(^.`class` := "prereq", "P"),
          <.div(^.`class` := "recipe", "R")
        )
      )
    }
  ).build

  val engramTable = ReactComponentB[(Survivor, Backend)]("EngramTable")
    .render( P => {
      val (s: Survivor, b: Backend) = P
      <.div(
        ^.`class` := "main",
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
        sideBar((S, B)),
        engramTable((S, B))
      )
  ).buildU


  def main(): Unit = {
    DocStyles.addToDocument()
    React.render(Page(), document.body)
  }


}
