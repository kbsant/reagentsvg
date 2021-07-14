# reagentsvg

SVG charting demo using clojure and reagent

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
