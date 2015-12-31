(ns ironmaiden.core
  (:gen-class)
  (:import (ironmaiden Native)))

(defn -main
  "I don't do a whole lot."
  [& args]
  (clojure.lang.RT/loadLibrary "Native")
  (ironmaiden.Native/hello)
  (println args "Hell O World!"))
