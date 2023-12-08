@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LogEntryRepository logEntryRepository;

     public void selectCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        validateCourseSelection(student);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        student.getCourses().add(course);
        studentRepository.save(student);
    }

    private void validateCourseSelection(Student student) {
        // Validate business rule: Student should not select more than three courses
        if (student.getCourses().size() >= 3) {
            throw new RuntimeException("Student cannot register for more than three courses");
        }
    }

    public void logHours(Long studentId, LogEntryDto logEntryDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        validateLogEntry(student, logEntryDto);

        LogEntry logEntry = new LogEntry();
        logEntry.setStudent(student);
        logEntry.setDate(logEntryDto.getDate());
        logEntry.setCategory(logEntryDto.getCategory());
        logEntry.setDescription(logEntryDto.getDescription());
        logEntry.setTimeSpent(logEntryDto.getTimeSpent());

        logEntryRepository.save(logEntry);
    }

    private void validateLogEntry(Student student, LogEntryDto logEntryDto) {
        // Validate business rules for logging hours
        LocalDate today = LocalDate.now();
        if (!logEntryDto.getDate().equals(today)) {
            throw new RuntimeException("Log entry date must be today");
        }

        // Check if the student has already logged hours for the same date
        List<LogEntry> existingEntries = logEntryRepository.findByStudentIdAndDate(
                student.getId(), logEntryDto.getDate());

        if (!existingEntries.isEmpty()) {
            throw new RuntimeException("Student has already logged hours for today");
        }
    }

    public List<LogEntry> getLogEntries(Long studentId) {
        // Retrieve log entries for the given student
        return logEntryRepository.findByStudentId(studentId);
    }

    public void updateLog(Long studentId, Long logEntryId, LogEntryDto logEntryDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        LogEntry logEntry = logEntryRepository.findById(logEntryId)
                .orElseThrow(() -> new RuntimeException("Log entry not found"));

        // Validate that the log entry belongs to the student
        validateLogOwnership(student, logEntry);

        // Update log entry fields
        updateLogEntryFields(logEntry, logEntryDto);

        logEntryRepository.save(logEntry);
    }

    public void deleteLog(Long studentId, Long logEntryId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        LogEntry logEntry = logEntryRepository.findById(logEntryId)
                .orElseThrow(() -> new RuntimeException("Log entry not found"));

        // Validate that the log entry belongs to the student
        validateLogOwnership(student, logEntry);

        // Remove log entry from the student's logEntries list
        student.getLogEntries().remove(logEntry);

        // Delete the log entry
        logEntryRepository.delete(logEntry);
    }

    private void validateLogOwnership(Student student, LogEntry logEntry) {
        // Validate that the log entry belongs to the student
        if (!student.getLogEntries().contains(logEntry)) {
            throw new RuntimeException("Log entry does not belong to the student");
        }
    }

    private void updateLogEntryFields(LogEntry logEntry, LogEntryDto logEntryDto) {
        // Update log entry fields
        logEntry.setDate(logEntryDto.getDate());
        logEntry.setCategory(logEntryDto.getCategory());
        logEntry.setDescription(logEntryDto.getDescription());
        logEntry.setTimeSpent(logEntryDto.getTimeSpent());

        // Update other fields as needed
        // logEntry.setOtherField(logEntryDto.getOtherField());
    } } 
