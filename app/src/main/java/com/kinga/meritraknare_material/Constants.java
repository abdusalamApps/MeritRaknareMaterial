package com.kinga.meritraknare_material;

import com.kinga.meritraknare_material.data.CourseContract;
import com.kinga.meritraknare_material.data.CourseContract.CourseEntry;

/**
 * Created by kinga on 2018-03-13.
 */

public class Constants {

    public static final String KEY_ANIM_TYPE = "anim_type";
    public static final String KEY_TITLE = "anim_title";

    public enum TransitionType {
        FadeJava, FadeXML
    }

    public static final String FROM_MAIN = "from_main";

    public static final class GymnasiegemensammaKurser {
        public static final String[] ggCourses = {"SVE / SVA 1", "SVE / SVA 2", "SVE / SVA 3",
                "Engelska 5", "Engelska 6", "Historia 1b", "Idrott 1",
                "Matte 1", "Matte 2", "Matte 3", "Religion 1b", "Samhällskunskap 1b"};
        public static final double[] ggGrades = {20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0};
        public static final double[] ggPoints = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 50, 100};
        public static final int[] ggCoursesTypes = {CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM,
                CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM,
                CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM, CourseEntry.TYPE_GYM_GEM};


    }

    public static final class EkonomiKurser {
        public static final String[] ekonomiCourses = {"Företagsekonomi", "Privatjuridik", "Moderna språk", "Psykologi"};
        public static final double[] ekonomiGrades = {20.0, 20.0, 20.0, 20.0};
        public static final double[] ekonomiCoursesPoints = {100, 100, 100, 100};
        public static final int[] ekonomiCoursesTypes = {CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM};
    }

    public static final class EstetiskaKurser {
        public static final String[] estetiskaCourses = {"Estetisk kommunikation", "Konstarterna och samhället"};
        public static final double[] estetiskaGrades = {20.0, 20.0};
        public static final double[] estetiskaCoursesPoints = {100, 50};
        public static final int[] estetiskaCoursesTypes = {CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM};
    }

    public static final class HumanistiskaKurser {
        public static final String[] humanistiskaCourses = {"Filosofi", "Moderna språkt", "Människans språk"};
        public static final double[] humanistiskaCoursesGrades = {20.0, 20.0, 20.0};
        public static final double[] humanistiskaCoursesCoursesPoints = {50, 100, 100};
        public static final int[] humanistiskaCoursesCoursesTypes = {CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM};
    }

    public static final class NaturvetenskapsKurser {
        public static final String[] naturCourses = {"Biologi 1", "Fysik 1", "Kemi 1", "Moderna språk"};
        public static final double[] naturGrades = {20.0, 20.0, 20.0, 20.0};
        public static final double[] naturCoursesPoints = {100, 100, 100, 100};
        public static final int[] naturCoursesTypes = {CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM};
    }

    public static final class SamhallsvetenskapsKurser {
        public static final String[] samhallsCourses = {"Filosofi 1", "Moderna språk", "Psykologi", "Moderna språk"};
        public static final double[] samhallsGrades = {20.0, 20.0, 20.0, 20.0};
        public static final double[] samhallsCoursesPoints = {50, 100, 50, 100};
        public static final int[] samhallsCoursesTypes = {CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM};
    }

    public static final class TekniksKurser {
        public static final String[] tekniksCourses = {"Fysik 1", "Kemi 1", "Teknik 1"};
        public static final double[] tekniksGrades = {20.0, 20.0, 20.0};
        public static final double[] tekniksCoursesPoints = {150, 100, 150};
        public static final int[] tekniksCoursesTypes = {CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM, CourseEntry.TYPE_PROG_GEM};
    }

    public static final class ProgramsInts {
        public static final int EKONOMI = 0;
        public static final int ESTETISK = 1;
        public static final int HUMANISTISK = 2;
        public static final int INTERNATIONAL = 3;
        public static final int NATUR = 4;
        public static final int SAMHALL = 5;
        public static final int TEKNIK = 6;
    }
}
