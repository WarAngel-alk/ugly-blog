package com.my.util;

import com.my.model.Post;

import java.time.Month;
import java.time.Year;
import java.util.*;

public abstract class PostUtil {

    // Suppress constructor creation to avoid class instantiating
    private PostUtil() {
    }

    private static final int ARCHIVE_BEGIN_YEAR = 2013;
    private static final int ARCHIVE_BEGIN_MONTH = 1;

    /**
     * @param allPosts any collection of posts
     * @return map where key is a Integer with year<br/>
     *         value is another map where key is Integer with month. <br/>
     *         value of second map is List of all Post's published <br/>
     *         at this month
     */
    public static Map<Integer, Map<Integer, List<Post>>> groupPostsByYearAndMonth(final Collection<Post> allPosts) {
        SortedSet<Post> allPostsSorted = new TreeSet<>((Post o1, Post o2) -> {
            int compareResult = o1.getPostDate().compareTo(o2.getPostDate());

            // To allow posts with equivalent date
            return compareResult == 0 ? 1 : compareResult;
        });
        allPostsSorted.addAll(allPosts);

        Map<Integer, Map<Integer, List<Post>>> yearMap = new TreeMap<>();

        int startYear = Year.now().getValue();
        for (int year = startYear; year >= ARCHIVE_BEGIN_YEAR; --year) {
            Map<Integer, List<Post>> monthMap = new TreeMap<>();

            int month = (year == startYear) ? (GregorianCalendar.getInstance().get(Calendar.MONTH) + 1) : 12;
            while (month > 0 && (year >= ARCHIVE_BEGIN_YEAR && month >= ARCHIVE_BEGIN_MONTH)) {
                Post startPost = new Post(new GregorianCalendar(year, month - 1, 1).getTime());
                Post endPost = new Post(new GregorianCalendar(year, month - 1, daysInMonth(year, month), 23, 59, 59).getTime());

                List<Post> postsInMonth = new ArrayList<>();
                postsInMonth.addAll(allPostsSorted.subSet(startPost, endPost));

                if (postsInMonth.size() > 0) {
                    monthMap.put(month, postsInMonth);
                }

                --month;
            }

            yearMap.put(year, monthMap);
        }

        return yearMap;
    }

    private static int daysInMonth(int year, int month) {
        boolean leapYear = Year.isLeap(year);
        return Month.of(month).length(leapYear);
    }

}
