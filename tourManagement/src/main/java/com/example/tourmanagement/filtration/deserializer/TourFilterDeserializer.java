package com.example.tourmanagement.filtration.deserializer;

import com.example.tourmanagement.filtration.TourFilter;
import com.example.tourmanagement.filtration.impl.TourFilterRequest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@JsonComponent
public class TourFilterDeserializer extends JsonDeserializer<TourFilter> {

    private final Map<String, Class<? extends TourFilter>> filters;

    public TourFilterDeserializer(List<TourFilter> filterList) {

        filters = filterList.stream().collect(
                toMap(TourFilter::getOperation, TourFilter::getClass)
        );
    }

    @Override
    public TourFilter deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        ObjectNode root = mapper.readTree(parser);

        if (root.has("operation")) {
            String type = root.get("operation").asText();
            var clazz = filters.get(type);

            if (clazz != null)
                return mapper.readValue(root.toString(), clazz);
        }

        return mapper.readValue(root.toString(), TourFilterRequest.class);
    }
}