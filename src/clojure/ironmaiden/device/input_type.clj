(ns ironmaiden.device.input-type
  (:require [clojure.string :as string]))


(def header-filepath "/usr/include/linux/input-event-codes.h")


(defn init []
  (with-open [rdr (clojure.java.io/reader header-filepath)]
    (dorun
      (for [line (line-seq rdr)]
        (println line)))))
