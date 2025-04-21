package com.example.weather.runner;

import com.example.weather.steps.CommonSteps;
import com.example.weather.steps.NegativeTestSteps;
import com.example.weather.steps.PositiveTestSteps;
import io.qameta.allure.jbehave.AllureJbehave;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.PrintStreamStepMonitor;

public class TestRunner extends JUnitStories {
    public TestRunner() {
        super();
        configuredEmbedder().embedderControls()
                .doGenerateViewAfterStories(true)
                .doIgnoreFailureInStories(true) // false = fail build
                .doIgnoreFailureInView(true) // false = fail build
                .useStoryTimeouts("60");
    }

    @Override
    public Configuration configuration() {
        Configuration config = new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass().getClassLoader()))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withDefaultFormats()
                        .withFormats(Format.CONSOLE, Format.HTML, Format.XML)
                        .withReporters(new AllureJbehave())
                        .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                        .withRelativeDirectory("../build/jbehave")
                        .withFailureTrace(true))
                .useParameterConverters(new ParameterConverters().addConverters(new MapConverter()))
                .useStepMonitor(new PrintStreamStepMonitor(System.out));
        return config;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        System.out.println("Registering step classes: CommonSteps, PositiveTestSteps, NegativeTestSteps");
        return new InstanceStepsFactory(configuration(),
                new CommonSteps(),
                new PositiveTestSteps(),
                new NegativeTestSteps());
    }

    @Override
    protected List<String> storyPaths() {
        System.out.println("Loading story paths: stories/positive/current_weather_stories.story, stories/negative/api_error_stories.story");
        return Arrays.asList(
                "stories/positive/current_weather_stories.story",
                "stories/negative/api_error_stories.story"
        );
    }

    public static class MapConverter implements ParameterConverters.ParameterConverter {
        @Override
        public boolean accept(Type type) {
            if (type instanceof Class<?> && Map.class.isAssignableFrom((Class<?>) type)) {
                System.out.println("MapConverter.accept: Raw Map type accepted");
                return true;
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                Type[] typeArgs = parameterizedType.getActualTypeArguments();
                boolean isMap = rawType instanceof Class<?> && Map.class.isAssignableFrom((Class<?>) rawType);
                boolean isStringMap = typeArgs.length == 2 && typeArgs[0] == String.class && typeArgs[1] == String.class;
                System.out.println("MapConverter.accept: Parameterized type: " + type + ", isMap: " + isMap + ", isStringMap: " + isStringMap);
                return isMap && isStringMap;
            }
            System.out.println("MapConverter.accept: Type " + type + " not accepted");
            return false;
        }

        @Override
        public Object convertValue(Object value, Type type) {
            System.out.println("MapConverter.convertValue called with value: " + value + ", type: " + type);
            if (value instanceof String) {
                String tableString = (String) value;
                System.out.println("Converting table string: " + tableString);
                ExamplesTable table = new ExamplesTable(tableString);
                Map<String, String> result = new HashMap<>();
                List<Map<String, String>> rows = table.getRows();
                if (!rows.isEmpty()) {
                    Map<String, String> row = rows.get(0); // Use first row
                    row.forEach((k, v) -> {
                        System.out.println("Processing entry - Key: " + k + ", Value: " + v);
                        result.put(k.trim(), v.trim());
                    });
                }
                System.out.println("Converted Map: " + result);
                return result;
            }
            System.out.println("Returning null for value: " + value);
            return null;
        }
    }
}