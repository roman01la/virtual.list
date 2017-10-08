(ns example.core
  (:require [rum.core :as rum]
            [goog.dom :as gdom]
            [sablono.core :refer-macros [html]]
            [virtual.list :refer [v-list]]))

(rum/defc Avatar [label color]
  [:div {:style {:background-color color
                 :color            "#fff"
                 :width            42
                 :min-width        42
                 :height           42
                 :border-radius    "50%"
                 :box-sizing       "border-box"
                 :border           "2px solid #eee"
                 :display          "flex"
                 :align-items      "center"
                 :justify-content  "center"
                 :margin           "0 16px 0 4px"}}
   [:span {} label]])

(rum/defc InputField [label value on-change]
  [:div {}
   [:span {:style {:margin "0 8px 0 0"}}
    label]
   [:input {:value     value
            :type      "number"
            :on-change (comp on-change js/parseFloat #(.. % -target -value))}]])

(rum/defcs ContactsList <
  (rum/local
    {:row-height 48
     :rows-count 8}
    ::state)
  [{state ::state} data]
  (let [{:keys [row-height rows-count]} @state
        max-rows-count (count data)
        render-row (fn [{:keys [idx scrolling?]}]
                     (let [[n color] (nth data idx)]
                       (html [:div {:key   idx
                                    :style {:height        row-height
                                            :box-sizing    "border-box"
                                            :border-bottom (when (not= idx (dec max-rows-count)) "1px solid #ccc")
                                            :display       "flex"
                                            :align-items   "center"
                                            :padding       "0 4px"}}
                              (Avatar (inc n) color)
                              [:div {} "Lorem Ipsum is simply dummy text of the printing and typesetting industry"]])))]
    [:div {:style {:width "100%"
                   :font  "normal 16px sans-serif"}}
     [:div {:style {:display         "flex"
                    :justify-content "space-around"
                    :padding         8}}
      (InputField "Row height:" row-height #(swap! state assoc :row-height %))
      (InputField "Rows count:" rows-count #(swap! state assoc :rows-count %))]
     [:div {:style {:border        "1px solid #ccc"
                    :border-radius 4}}
      (v-list {:row-height     row-height
               :rows-count     rows-count
               :max-rows-count max-rows-count
               :render-row     render-row
               :overscan-count 2})]]))

(rum/mount
  (ContactsList (->> (range 1000) (mapv (fn [n] [n (str "#" (.toString (rand-int 16rFFFFFF) 16))]))))
  (gdom/getElement "example"))
