(ns ironmaiden.util
  (:require [clojure.string :as string]))


#_(re-matches-case
  "fooo"
  ("foo (.+) bar" [_ center] (cat center))
  'hoge)


; TODO let value

(defmacro re-match-case [& clauses]
  (when-let [[value clause & more] clauses]
    (if (list? clause)
      (let [[pat bindings & expr] clause]
        `(if-let [~bindings (re-matches ~pat ~value)]
           (do
             ~@expr)
           (re-match-case ~value ~@more)))
      clause)))
