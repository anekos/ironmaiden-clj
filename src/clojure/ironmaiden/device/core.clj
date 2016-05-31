(ns ironmaiden.device.core)


(def ^:dynamic *buffer-size* (atom 1000))


(defprotocol Device
  (channel [this])
  (stop [this]))
