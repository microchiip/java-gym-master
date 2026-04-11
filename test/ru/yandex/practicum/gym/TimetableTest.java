package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, monday.size(), "За понедельник должна вернуться одна тренировка");
        assertEquals(0, tuesday.size(), "За вторник не должно быть тренировок");
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, monday.size(), "За понедельник должна вернуться одна тренировка");

        assertEquals(2, thursday.size(), "За четверг должны вернуться две тренировки");
        assertEquals(13, thursday.get(0).getTimeOfDay().getHours(), "Первая тренировка в четверг должна быть в 13:00");
        assertEquals(20, thursday.get(1).getTimeOfDay().getHours(), "Вторая тренировка в четверг должна быть в 20:00");

        assertEquals(0, tuesday.size(), "За вторник не должно быть тренировок");
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondayAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        List<TrainingSession> mondayAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        assertEquals(1, mondayAt13.size(), "За понедельник в 13:00 должна вернуться одна тренировка");
        assertEquals(0, mondayAt14.size(), "За понедельник в 14:00 не должно быть тренировок");
    }

    @Test
    void testGetTrainingSessionsEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> mondayAt10 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        assertNotNull(monday, "Должен вернуться список, а не null");
        assertEquals(0, monday.size(), "Список должен быть пустым");
        assertNotNull(mondayAt10, "Должен вернуться список, а не null");
        assertEquals(0, mondayAt10.size(), "Список должен быть пустым");
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");
        Group groupChild = new Group("Гимнастика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Гимнастика для взрослых", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(groupChild, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> wednesdayAt10 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));

        assertEquals(2, wednesdayAt10.size(), "В среду в 10:00 должны быть две тренировки");
    }

    @Test
    void testGetTrainingSessionsForDaySortedOrder() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Смирнов", "Алексей", "Викторович");
        Group group = new Group("Йога", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(8, 0)));

        List<TrainingSession> friday = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);

        assertEquals(3, friday.size(), "Должно быть три тренировки в пятницу");
        assertEquals(8, friday.get(0).getTimeOfDay().getHours(), "Первая должна быть в 8:00");
        assertEquals(12, friday.get(1).getTimeOfDay().getHours(), "Вторая должна быть в 12:00");
        assertEquals(20, friday.get(2).getTimeOfDay().getHours(), "Третья должна быть в 20:00");
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertNotNull(result, "Должен вернуться список, а не null");
        assertEquals(0, result.size(), "Список тренеров должен быть пустым");
    }

    @Test
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(1, result.size(), "Должен быть один тренер");
        assertEquals(3, result.get(0).getCount(), "У тренера должно быть 3 тренировки");
    }

    @Test
    void testGetCountByCoachesMultipleCoachesSortedByCount() {
        Timetable timetable = new Timetable();

        Coach coachA = new Coach("Иванов", "Иван", "Иванович");   // 1 тренировка
        Coach coachB = new Coach("Петров", "Пётр", "Петрович");   // 3 тренировки
        Coach coachC = new Coach("Сидоров", "Сидор", "Сидорович"); // 2 тренировки
        Group group = new Group("Гимнастика", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(group, coachA,
                DayOfWeek.MONDAY, new TimeOfDay(9, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coachB,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachB,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachB,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coachC,
                DayOfWeek.TUESDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachC,
                DayOfWeek.THURSDAY, new TimeOfDay(18, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(3, result.size(), "Должно быть три тренера");

        assertEquals(3, result.get(0).getCount(), "Первым должен быть тренер с 3 тренировками");
        assertEquals(2, result.get(1).getCount(), "Вторым должен быть тренер с 2 тренировками");
        assertEquals(1, result.get(2).getCount(), "Третьим должен быть тренер с 1 тренировкой");
    }
}

