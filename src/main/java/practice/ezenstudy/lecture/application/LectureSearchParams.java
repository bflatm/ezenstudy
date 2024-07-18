package practice.ezenstudy.lecture.application;

public record LectureSearchParams(
        String sort,
        String title,
        String teacherName,
        String category,
        String studentId,
        Integer pageNumber,
        Integer pageSize
) {
    // 아래 코멘트 처리되어 있는 constructor와 동일한 기능의 compact constructor
    public LectureSearchParams {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 5 : pageSize;
    }

//    public LectureSearchParams(String sort, String title, String teacherName, String category, String studentId, Integer pageNumber, Integer pageSize) {
//        this.sort = sort;
//        this.title = title;
//        this.teacherName = teacherName;
//        this.category = category;
//        this.studentId = studentId;
//        this.pageNumber = pageNumber == null ? 1 : pageNumber;
//        this.pageSize = pageSize == null ? 5 : pageSize;
//    }

    // Getter
    public Integer offset() {
        return (pageNumber - 1) * pageSize;
    }
}
