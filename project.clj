(defproject ironmaiden "0.1.0-SNAPSHOT"
  :description "The Input Manager"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ironmaiden.core
  :aot [ironmaiden.core]
  :jar-name "ironmaiden.jar"
  :uberjar-name "ironmaiden-standalone.jar"
  :auto-clean false
  :java-source-paths ["src/java"]
  :source-paths ["src/clojure"]
  :jvm-opts ["-Djava.library.path=lib/"])
