# reagentsvg

SVG charting demo using clojure and reagent

<div><style type="text/css" scoped="">
.g {
fill: black;
}
g text {
fill: white;
}
.bar {
stroke: silver;
stroke-width: 3;
fill: cornflowerblue;
}
</style><svg width="300" height="300"><title>Bar Chart</title><g transform="rotate(-90) translate(-300,0)"><rect class="g" width="100%" height="100%"></rect><svg x="5" y="5"><rect class="bar" width="240" height="60" rx="10"></rect><text x="250" y="25" dy="10pt">max</text></svg><svg x="5" y="65"><rect class="bar" width="48" height="60" rx="10"></rect><text x="58" y="25" dy="10pt">min</text></svg><svg x="5" y="125"><rect class="bar" width="120" height="60" rx="10"></rect><text x="130" y="25" dy="10pt">med</text></svg><svg x="5" y="185"><rect class="bar" width="144" height="60" rx="10"></rect><text x="154" y="25" dy="10pt">ave</text></svg></g></svg></div>

<div><style type="text/css" scoped="">
.g {
fill: black;
}
g text {
fill: white;
}
.bar {
stroke: silver;
stroke-width: 3;
fill: cornflowerblue;
}
</style><svg width="300" height="300"><title>Bar Chart</title><g><rect class="g" width="100%" height="100%"></rect><svg x="5" y="5"><rect class="bar" width="240" height="60" rx="10"></rect><text x="250" y="25" dy="10pt">max</text></svg><svg x="5" y="65"><rect class="bar" width="48" height="60" rx="10"></rect><text x="58" y="25" dy="10pt">min</text></svg><svg x="5" y="125"><rect class="bar" width="120" height="60" rx="10"></rect><text x="130" y="25" dy="10pt">med</text></svg><svg x="5" y="185"><rect class="bar" width="144" height="60" rx="10"></rect><text x="154" y="25" dy="10pt">ave</text></svg></g></svg></div>

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

# License

MIT license, same as the above template.
