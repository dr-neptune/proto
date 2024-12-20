(ns proto.client.core
  (:require [goog.dom :as gdom]
            [goog.events :as events]))

(defn get-element [id] (gdom/getElement id))

(defn draw-points [ctx points]
  (when (seq points)
    (.beginPath ctx)
    (let [[x0 y0] (first points)]
      (.moveTo ctx (+ 400 x0) (+ 200 y0)))
    (doseq [[x y] (rest points)]
      (.lineTo ctx (+ 400 x) (+ 200 y)))
    (.stroke ctx)))

(defn fetch-brownian [steps step-size callback]
  (let [url (str "/brownian?steps=" steps "&step-size=" step-size)]
    (-> (js/fetch url)
        (.then #(.json %))
        (.then callback)
        (.catch (fn [err] (js/console.error "Fetch error: " err))))))

(defn draw-brownian! []
  (let [steps (js/parseInt (.-value (get-element "steps")))
        step-size (js/parseFloat (.-value (get-element "step-size")))
        canvas (get-element "canvas")
        ctx (.getContext canvas "2d")]
    (.clearRect ctx 0 0 (.-width canvas) (.-height canvas))
    (fetch-brownian steps step-size
                    (fn [data]
                      (let [points (.-points data)]
                        (draw-points ctx points))))))

(defn init []
  (js/console.log "Frontend initialized")
  (let [btn (get-element "draw-button")]
    (events/listen btn "click" draw-brownian!)))
