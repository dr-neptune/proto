(ns proto.server.system
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [proto.server.routes :as routes]))

(def app
  (-> routes/app-routes (wrap-resource "public") (wrap-content-type)))

(defn -main [& _args]
  (println "Starting server on http://localhost:3000")
  (run-jetty app {:port 3000}))
