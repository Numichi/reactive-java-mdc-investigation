package com.example.reactivejavamdc.log4j2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;
import java.util.Map;

@Plugin(name = "ExamplePlugin", category = Node.CATEGORY, elementType = Appender.ELEMENT_TYPE)
public class ExamplePlugin extends AbstractStringLayout {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @PluginFactory
    public static ExamplePlugin pluginFactory() {
        return new ExamplePlugin();
    }
    
    public ExamplePlugin() {
        super(Charset.defaultCharset());
    }
    
    @Override
    public String toSerializable(LogEvent event) {
        var model = new LogModel();
        model.setMessage(event.getMessage().getFormattedMessage());
        model.setContext(event.getContextData().toMap());
    
        try {
            return mapper.writeValueAsString(model) + "\n";
        } catch (JsonProcessingException e) {
            return e.getMessage() + "\n";
        }
    }
    
    
    static class LogModel {
        private String message;
        private Map<String, String> context;
    
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    
        public Object getContext() {
            return context;
        }
        public void setContext(Map<String, String> context) {
            this.context = context;
        }
    }
}
