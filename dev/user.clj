(ns user
  (:import (ironmaiden Native))
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.pprint :refer [pp pprint cl-format]]))


(defn init []
  (clojure.lang.RT/loadLibrary "Native"))

(defn reset []
  (set! *print-level* 2)
  (set! *print-length* 10)
  (refresh))
