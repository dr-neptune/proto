(ns proto.server.handlers
  (:require [proto.domain.core :as domain]
            [clojure.edn :as edn]
            [clojure.data.json :as json]))

(defn brownian-handler [req]
  (let [query-params (-> req :query-string (java.net.URLDecoder/decode "UTF-8"))
        params (into {}
                     (for [pair (if query-params (clojure.string/split query-params #"&") [])]
                       (let [[k v] (clojure.string/split pair #"=")]
                         [(keyword k) (edn/read-string v)])))
        steps (or (:steps params) 1000)
        step-size (or (:step-size params) 1.0)
        data (domain/brownian-motion steps step-size)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-str {:points data})}))

(defn index-handler [_]
  {:status 200 :headers {"Content-Type" "text/html"}
   :body (slurp (clojure.java.io/resource "public/index.html"))})

(defn not-found-handler [_]
  {:status 404 :headers {"Content-Type" "text/plain"} :body "Not Found"})
