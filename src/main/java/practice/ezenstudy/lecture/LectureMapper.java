package practice.ezenstudy.lecture;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LectureMapper {
    int countAll(LectureSearchParams params);

    List<LectureResponse> findAll(LectureSearchParams params);
}
