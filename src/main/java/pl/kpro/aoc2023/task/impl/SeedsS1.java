package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.*;

public class SeedsS1 implements AdventTask {
    @Override
    public String getName() {
        return "SeedsS1";
    }

    @Override
    public int getNumber() {
        return 9;
    }

    @Override
    public String run(String input) {
        /*
        list of N sources
        destination | source | length
        -> task: map sources through many tables and order by lowest
         */
        String[] inputSections = input.split("\n\n");
        List<Long> sourceItems = this.getSourceItems(inputSections[0]);
        List<Map<Range, Long>> mappings = new LinkedList<>();
        for (int i = 1; i < inputSections.length; i++) {
            mappings.add(this.generateMap(inputSections[i]));
        }

        Map<Long, Long> sourceResults = new HashMap<>();
        for (Long source : sourceItems) {
            Long lastMappingResult = source;
            for(Map<Range, Long> mapping : mappings) {
                Long finalLastMappingResult = lastMappingResult;
                Optional<Map.Entry<Range, Long>> selectedMapping = mapping.entrySet().stream()
                        .filter(ranges -> ranges.getKey().isWithin(finalLastMappingResult)).findFirst();
                if(selectedMapping.isPresent()) {
                    lastMappingResult = selectedMapping.map((ranges) -> ranges.getValue() + (finalLastMappingResult - ranges.getKey().start)).get();
                }
            }
            sourceResults.put(source, lastMappingResult);
        }
        return sourceResults.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue().toString();
    }

    private List<Long> getSourceItems(String inputLine) {
        return Arrays.stream(inputLine.substring(inputLine.indexOf(':')+1).trim().split(" ")).map(Long::valueOf).toList();
    }

    private Map<Range, Long> generateMap(String mapDefinition) {
        Map<Range, Long> mapping = new HashMap<>();
        String[] mapDefinitionChunks = mapDefinition.substring(mapDefinition.indexOf(':')+1).trim().split("\n");
        for(String chunk : mapDefinitionChunks) {
            String[] chunkParts = chunk.split(" ");
            Long targetStart = Long.valueOf(chunkParts[0].trim());
            Long sourceStart = Long.valueOf(chunkParts[1].trim());
            Long stepCount = Long.valueOf(chunkParts[2].trim());

            mapping.put(new Range(sourceStart, sourceStart+stepCount), targetStart);
        }
        return mapping;
    }

    private class Range {
        final Long start,end;

        boolean isWithin(Long i) {
            return start <= i && end > i;
        }

        Range(Long s, Long e) {
            this.start =s;
            this.end =e;
        }
    }
}
