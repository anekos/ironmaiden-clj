(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.pprint :refer [pp pprint cl-format]]
            [ironmaiden.device.input-type :refer :all]))


(defn reset []
  (set! *print-level* 2)
  (set! *print-length* 10)
  (refresh))
