# reagentsvg

SVG charting demo using clojure and reagent

## Works with static pages, too!

Although this demo uses reagent, the svg code is in cljc files, which means they can be used to render static pages using hiccup.

## Sample Rendering

Line chart

![Line chart](sample-linechart.svg?raw=true)

Vertical bar chart

![Vertical bar chart](sample-barchart-v.svg?raw=true)

Horizontal bar chart

![Horizontal bar chart](sample-barchart-h.svg?raw=true)

# Development

## Dependencies

This project depends on:

* java
* leiningen - installation steps are at <https://leiningen.org>
* npm 
* shadow-cljs - install with `npm -g shadow-cljs`

## Run in dev mode

Run the project in the browser with:

    shadow-cljs watch app

# Building

To build the output, run:

    lein uberjar

This will build a jar file.

# Running

Use java to run the jar:

    java -jar target/reagentsvg.jar
    
    
# Attribution

The initial project was generated using a template: <https://github.com/reagent-project/reagent-template>
Referred to <https://developer.mozilla.org> for the SVG documentation.

The following are original works, covered by the license below:
* cljc/reagentsvg/graph.cljc
* cjls/reagentsvg/core.cljs
* sample-*.svg

# License

MIT license.
