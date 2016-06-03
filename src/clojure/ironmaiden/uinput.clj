(ns ironmaiden.uinput
  (:import (ironmaiden Native)))


(defn new-uinput
  "Make UInput device, and returns the fd"
  []
  (ironmaiden.Native/newUInput))


(defn send-event [fd type code value]
  (ironmaiden.Native/sendEvent fd type code value))


(defn send-syn [fd]
  (send-event fd 0 0 0))


(defn close-uinput [fd]
  (ironmaiden.Native/destroyUInput fd))
