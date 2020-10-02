# feature


# Feature Toggle Annotations
Java Feature Annotation


## The Idea
The initial idea came from the this article from [martinfowler.com](https://martinfowler.com/articles/feature-toggles.html) on how to handle feature toggles.
My idea is to add an annotation to web endpoints that are based on any specific feature.


## Usage


### Configuration
#### Manual
The interface `FeatureChecker` has a method called `allowFeature` that should return a boolean to if a feature should be allowed.

```
@Bean
public FeatureResolver featureResolver() {
    return new FeatureResolver() {
        @Override
        public boolean isAllowed(String featureName) {
            return "helloFeature".equals(featureName);
        }
    };
}
```

#### PropertiesFeatureResolver
This uses the Spring Framework core `PropertiesLoaderUtils` to load the properties file. This will throw a `RunTimeException` if there is an error processing the properties file.   

|  Constructor Arg | Description | Default |
| ---        | ---         | ---     |
| `propertiesFile` | The properties file to read in | `classpath:features.properties` 
| `isNotFoundFeatureEnabled` | This tells the resolver how to handle a property that is not found in the properties file. If set to `false` the resolver will treat unknown features as if they were not allowed. | `false` |
| `propertiesPrefix` | Optional prefix that can be used for features. It will prepend via a `.` (dot) between the prefix and the value passed into the `@Feature`.  | null |

Default usage:
```
@Bean
public FeatureResolver featureResolver() {
    return PropertiesFeatureResolver.builder()
        .propertiesFile("")
        .propertiesPrefix(null)
        .isNotFoundFeatureEnabled(false)
        .build();
}
```

### Adding to controller endpoint
```
@Feature("helloFeature")
@RequestMapping("/hello")
@ResponseBody
public String init() {
    Map<String, Object> response = new HashMap<>();

    System.out.println("This will be displayed");
    response.put("message", "Hello World!");

    return response;
}

@Feature("byeFeature")
@RequestMapping("/bye")
@ResponseBody
public String init() {
    Map<String, Object> response = new HashMap<>();

    System.out.println("This will *NOT* be displayed");
    response.put("message", "Bye World!");

    return response;
}
```
