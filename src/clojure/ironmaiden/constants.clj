(ns ironmaiden.constants)


(def header-filepath "/usr/include/linux/input-event-codes.h")


(defn init []
  (with-open [rdr (clojure.java.io/reader header-filepath)]
    (dorun
      (for [line (line-seq rdr)]
        (println line)))))
