(ns proto.server.routes
  (:require [proto.server.handlers :as h]))

(defn app-routes [req]
  (case (:uri req)
    "/" (h/index-handler req)
    "/brownian" (h/brownian-handler req)
    (h/not-found-handler req)))
