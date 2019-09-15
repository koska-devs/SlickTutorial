// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/koska/devlopment/play-samples-play-scala-hello-world-tutorial/conf/routes
// @DATE:Sun Sep 15 11:59:59 JST 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
