import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScheduleParser {
    public static String parse(String groupNumber) throws IOException {
        String groupSchedulePageUrl = findGroupSchedulePage(groupNumber);
        if (groupSchedulePageUrl == null) {
            return null;
        }

        Document doc = Jsoup.connect(groupSchedulePageUrl).get();

        // if page contains class "schedule__empty" then there is no schedule and return null
        Elements schedule = doc.select(".schedule__empty");
        if (schedule.first() != null) {
            return null;
        }

        // if page doesn't contain class "schedule__empty", then there is schedule
        schedule = doc.select(".schedule__day");
        StringBuilder answer = new StringBuilder();
        for (Element day : schedule) {
            answer.append("<b>" + day.select(".schedule__date").text() + "</b>" + "\n"); // added date
            Elements lessons = day.select(".lesson");
            int count = 1;
            for (Element lesson : lessons) {
                answer.append("<b>" + count++ + ") " + lesson.select(".lesson__subject").text() + "</b>" + "\n");
                String lessonType = lesson.select(".lesson__type").text();
                switch (lessonType) {
                    case "Лекции":
                        answer.append(Emoji.SPEAKING_HEAD_IN_SILHOUETTE);
                        break;
                    case "Практика":
                        answer.append(Emoji.MEMO);
                        break;
                    case "Лабораторные":
                        answer.append(Emoji.MICROSCOPE);
                        break;
                    case "Консультации":
                        answer.append(Emoji.QUESTION);
                        break;
                    case "Экзамен":
                        answer.append(Emoji.WRITING_HAND);
                        break;
                    default:
                        answer.append(Emoji.BRAIN);
                        break;
                }
                answer.append(" " + lessonType + "\n");

                String lessonTeacher = lesson.select(".lesson__teachers").text();
                switch (lessonTeacher.charAt(lessonTeacher.length() - 1)) {
                    case 'а':
                        answer.append(Emoji.WOMAN_TEACHER);
                        break;
                    default:
                        answer.append(Emoji.MAN_TEACHER);
                        break;
                }
                answer.append(" " + lessonTeacher + "\n");
                answer.append(lesson.select(".lesson__places").text() + "\n");
            }
            answer.append("\n");
        }



        return answer.toString();
    }

    /**
     * @param groupNumber (example: "3530903/80001)
     * @return returns url of the group schedule page
     */
    private static String findGroupSchedulePage(String groupNumber) throws IOException {
        Document doc = Jsoup.connect("http://ruz.spbstu.ru/search/groups?q=" + groupNumber.substring(0, 7) + "%2F" + groupNumber.substring(8)).get();
        Elements groupsElements = doc.select(".groups-list__link"); // list of groups found by @param groupNumber
        for (Element group : groupsElements) {
            if (group.text().equals(groupNumber)) {
                return "http://ruz.spbstu.ru" + group.attr("href");
            }
        }

        return null;
    }
}
