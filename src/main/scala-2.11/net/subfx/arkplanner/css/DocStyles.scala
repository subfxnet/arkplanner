package net.subfx.arkplanner.css

import scalacss.Defaults._

/**
 * Stylesheets
 */
object DocStyles extends StyleSheet.Inline {

  import net.subfx.arkplanner.js.Engrams.engramList
  import dsl._

  val engramBgImg = styleF(Domain.ofValues(engramList:_*))(b =>
    styleS(
      &.before(
        content := "\" \"",
        display.block,
        position.absolute,
        left.`0`,
        top.`0`,
        width(100.%%),
        height(100.%%),
        zIndex(1),
        opacity(0.4),
        backgroundRepeat := "no-repeat",
        backgroundPosition := "50% 0",
        backgroundSize := "cover",
        backgroundImage := "url(\"%s\")".format(b.imageUrl)
      )
    )
  )

}
