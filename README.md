<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/joszamama/xatkit-spl-parser"></a>
  <h3 align="center">JSON to Xatkit Chatbot JAVA Parser</h3>
  <p align="center">
    Easy and replicable JSON to Xatkit Definition parser
    <br />
    <br />
    <a href="https://github.com/joszamama/xatkit-spl-parser/issues">Report Bug</a>
    ·
    <a href="https://github.com/joszamama/xatkit-spl-parser/issues">Request Feature</a>
  </p>
</div>

<!-- ABOUT THE PROJECT -->
## About The Project

One of the main features I have been missing in Xatkit has been to be able to create basic chatbots in a simple way, making API calls, or using a standardized format such as JSON for the definition of a chatbot. This can make the barrier of entry to the project much more friendly.

Here's why:
* I don't need to fully understand how to work the Fluent definition or the internal DSL that Xatkit employs when designing a chatbot.
* I don't need to install project dependencies such as Maven, JDK, Git... in order to run a chatbot, whose internal technology is transparent to the standard user.
* I don't need to learn Java to be able to create a chatbot, whose internal technology is transparent to the standard user.

Of course it is important to know all these technologies, but only if I want to contribute to the project by collaborating. A standard user does not have to know about all these topics, and can make potential users remain potential users. For this, I have designed a JSON parser to Xatkit Chatbot Java Definition (XCJD) so that from any web service, using a standardized and visual language like JSON that is also compatible with any web application, you can create the Java file that you will then run to raise your amazing chatbot.

The main goal is to consume this project from any other project where you want to use this parser. Specifically, for Xatkit-SPL-Backend, we have added this project as a submodule and when the user creates his Chatbot from the interface in a simple and visual way, this generates the JSON that will be transformed into XCJD.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* [Java](https://www.java.com/es/)
* [Maven](https://maven.apache.org/)
* [JSON Parser](https://docs.oracle.com/javaee/7/api/javax/json/stream/JsonParser.html)
* [JSON Path](https://github.com/json-path/JsonPath)


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Clone the repo
   ```sh
   git clone https://github.com/joszamama/xatkit-spl-parser.git
   ```
2. Install the Maven dependencies
   ```sh
   cd xatkit-spl-parser
   mvn clean compile
   ```
3. Añade el JSON de tu chatbot a la carpeta ./src/bots y ejecuta la siguiente instrucción:
   ```js
   mvn exec:java -e -Dexec.mainClass="com.xatkit.spl.parser.XatkitParser" -Dexec.args="./src/bots/JSON_FILE"
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the EPL-2.0 license. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>
