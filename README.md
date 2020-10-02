# feature


# Feature Toggle Annotations
Java Feature Annotation


## The Idea
The initial idea came from the this article from [martinfowler.com](https://martinfowler.com/articles/feature-toggles.html) on how to handle feature toggles.
My idea is to add an annotation to web endpoints that are based on any specific feature.


## Usage
### Configuration
```
@Bean
public FeatureResolver featureResolver() {
    return new FeatureResolver()
        .addDefaultFeatureChecker(new FeatureChecker() {
            @Override
            public boolean allowFeature(String featureName) {
                return "helloFeature".equals(featureName);
            }
        });
}
```

### Adding to controller endpoint
```
@Feature("helloFeature")
@RequestMapping("/hello")
public String init(Model model) {
    model.addAttribute("message", "Hello World!");
    return "myCoolFeaturePage";
}
```
