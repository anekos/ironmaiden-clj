(ns ironmaiden.constants
  (:require [clojure.string :as st]
            [ironmaiden.util :refer :all]))


(def header-filepath "/usr/include/linux/input-event-codes.h")


(defn lispize [name]
  (-> name
    (st/replace #"_" "-")
    (st/lower-case)
    symbol))

                             #_(intern 'ironmaiden.constants
                                     sym
                                     (read-string value))

                                  ;(let [sym (lispize name)]
                                  ;  (if (resolve sym)
                                  ;    (recur tail result)
                                  ;    (recur tail
                                  ;           (cons [name type value])))))

(defn init []
  (with-open [rdr (clojure.java.io/reader header-filepath)]
    (intern 'ironmaiden.constants
            'value-to-symbol
            (loop [[[_ name type value] & tail]
                   (compact
                     (map #(re-find #"#define ((\S+?)_\S+)\s+([x\da-f]+)" %)
                          (line-seq rdr)))
                   table {:ev {} :key {}}]
              (if name
                (let [sym (lispize name)
                      int-value (read-string value)]
                  (if (resolve sym)
                    (recur tail table)
                    (do
                      (intern 'ironmaiden.constants
                              sym
                              int-value)
                      (recur tail
                             (assoc-in
                               table
                               [(keyword (lispize type)) int-value]
                               sym)))))
                table)))))


(init)
