(ns user
  (:import (ironmaiden Native))
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.pprint :refer [pp pprint cl-format]]
            [clojure.repl :refer :all]
            [ironmaiden.util :refer :all]
            [ironmaiden.uinput :refer :all]
            [ironmaiden.constants :as c]
            [ironmaiden.device.keyboard :refer :all]))



(defn init []
  (clojure.lang.RT/loadLibrary "Native")
  (alter-var-root (var *print-level*) (fn [_] 2))
  (alter-var-root (var *print-length*) (fn [_] 10)))


(defn reset []
  (init)
  (refresh))


(init)
