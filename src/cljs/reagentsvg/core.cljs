(ns reagentsvg.core
  (:require
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [reagentsvg.graph :as graph]
   ))

(def bar-config
  {:title "Bar Chart"
   :width 300
   :height 300
   :tags #{:vertical}})

(def bar-data
  [{:label "abc"
    :value 100}
   {:label "def"
    :value 20}
   {:label "ghi"
    :value 50}
   {:label "jkl"
    :value 60}])

(def line-config
  {:title "Line Chart"
   :width 300
   :height 300})

(def line-data
  [{:label "mno"
    :value 100}
   {:label "qrs"
    :value 20}
   {:label "tuv"
    :value 50}
   {:label "wxy"
    :value 60}])

(defonce state
  (reagent/atom
   {:bar-data bar-data
    :line-data line-data}))

(defn home-page []
  (fn []
    (let [{:keys [line-data bar-data last-line-kv last-bar-kv]} @state]
      [:div.main
       [:h1 "Welcome to reagentsvg"]
       [:p (str "Last line update: " last-line-kv)]
       [:p "Line graph"]
       (graph/line-graph line-config line-data)
       [:p (str "Last bar update: " last-bar-kv)]
       [:p "Vertical bar graph"]
       (graph/bar-graph bar-config bar-data)
       [:p "Horizontal graph"]
       (graph/bar-graph (-> bar-config
                            (assoc :colors ["firebrick" "teal" "goldenrod"])
                            (dissoc :tags))
                        (:bar-data @state))])))

(defn random-update []
  (let [bar-key (rand-int (count bar-data))
        bar-label (get-in bar-data [bar-key :label])
        bar-value (rand-int 100)
        line-key (rand-int (count line-data))
        line-label (get-in line-data [line-key :label])
        line-value (rand-int 100)]
    (swap! state
           #(-> %
                (assoc-in [:bar-data bar-key :value] bar-value)
                (assoc-in [:line-data line-key  :value] line-value)
                (assoc :last-bar-kv (str bar-label " " bar-value))
                (assoc :last-line-kv (str line-label " " line-value))))))

(defn init! []
  (rdom/render [home-page] (.getElementById js/document "app"))
  (js/setInterval random-update 3000))
