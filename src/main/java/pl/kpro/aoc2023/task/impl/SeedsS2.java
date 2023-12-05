package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.*;
import java.util.stream.LongStream;

public class SeedsS2 implements AdventTask {
    @Override
    public String getName() {
        return "SeedsS2";
    }

    @Override
    public int getNumber() {
        return 10;
    }

    @Override
    public String run(String input) {
        String[] inputSections = input.split("\n\n");
        List<Map<Range, Long>> mappings = new LinkedList<>();
        for (int i = 1; i < inputSections.length; i++) {
            mappings.add(this.generateMap(inputSections[i]));
        }
        List<Long> results = new ArrayList<>();

        Long[] inputs = Arrays.stream(inputSections[0].substring(inputSections[0].indexOf(':')+1).trim().split(" ")).map(Long::valueOf).toArray(Long[]::new);
        for (int i = 0; i < inputs.length; i+=2) {
            System.out.println("Proceed to calculations: " + ((i/2)+1) + " / " +inputs.length/2);
            results.add(LongStream.range(inputs[i], inputs[i] + inputs[i+1]).map(l -> findResultFor(l, mappings)).min().getAsLong());
        }
        return results.stream().min(Comparator.naturalOrder()).toString();
    }

    private Long findResultFor(long sourceItem, List<Map<Range, Long>> mappings) {
        Map<Long, Long> sourceResults = new HashMap<>();
        Long lastMappingResult = sourceItem;
        for(Map<Range, Long> mapping : mappings) {
            Long finalLastMappingResult = lastMappingResult;
            Optional<Map.Entry<Range, Long>> selectedMapping = mapping.entrySet().stream()
                    .filter(ranges -> ranges.getKey().isWithin(finalLastMappingResult)).findFirst();
            if(selectedMapping.isPresent()) {
                lastMappingResult = selectedMapping.map((ranges) -> ranges.getValue() + (finalLastMappingResult - ranges.getKey().start)).get();
            }
        }
        return lastMappingResult;
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
