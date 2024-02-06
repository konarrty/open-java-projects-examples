package com.example.testbyport.mapper;

import com.example.testbyport.domain.Range;
import com.example.testbyport.entity.Task;
import com.example.testbyport.utils.DateUtils;
import dto.TaskByMonthDTO;
import dto.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

import static com.example.testbyport.utils.TaskWithRangeFormatterUtils.formatTaskWithRange;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = DateUtils.class)
public interface TaskMapper {
    Collection<TaskDTO> toDto(Iterable<Task> task);

    @Mapping(target = "range", source = "range")
    TaskByMonthDTO toByMonthWithRange(Task task, Range range);

    default Collection<TaskByMonthDTO> toByMonthWithRange(List<Task> tasks, List<Range> ranges) {

        return formatTaskWithRange(tasks.size(),
                (i) -> toByMonthWithRange(tasks.get(i), ranges.get(i)));
    }
}