(ns ironmaiden.constants
  (:require [clojure.string :as st]
            [ironmaiden.util :refer :all]))


(def header-filepath "/usr/include/linux/input-event-codes.h")


(defn lispize [name]
  (-> name
    (st/replace #"_" "-")
    (st/lower-case)
    symbol))


(defn init []
  (with-open [rdr (clojure.java.io/reader header-filepath)]
    (dorun
      (for [line (line-seq rdr)]
        (re-matches-case
          line
          (#"#define (\S+)\s+([x\d]+)"
           [_ name value]
           (let [sym (lispize name)]
             (when-not (resolve sym)
               (eval `(def ~sym ~(read-string value)))))))))))


(init)
