package domain;

import java.util.Arrays;

public class Match {
    int id;
    Competition competition;
    Season season;
    String utcDate;
    String status;
    int matchday;
    String stage;
    Object group;
    String lastUpdated;
    Odds odds;
    Score score;
    Team homeTeam;
    Team awayTeam;
    Referee[] referees;




    class Competition {
        int id;
        String name;
        Area area;

        class Area {
            String name;
            String code;
            String ensignUrl;

            @Override
            public String toString() {
                return "Area{" +
                        "name='" + name + '\'' +
                        ", code='" + code + '\'' +
                        ", ensignUrl='" + ensignUrl + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Competition{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", area=" + area +
                    '}';
        }
    }

    class Season {
        int id;
        String startDate;
        String endDate;
        int currentMatchday;
        Object winner;

        @Override
        public String toString() {
            return "Season{" +
                    "id=" + id +
                    ", startDate='" + startDate + '\'' +
                    ", endDate='" + endDate + '\'' +
                    ", currentMatchday=" + currentMatchday +
                    ", winner=" + winner +
                    '}';
        }
    }

    class Odds {
        String msg;

        @Override
        public String toString() {
            return "Odds{" +
                    "msg='" + msg + '\'' +
                    '}';
        }
    }

    class Score {
        String winner;
        String duration;
        Period fullTime;
        Period halfTime;
        Period extraTime;
        Period penalties;

        @Override
        public String toString() {
            return "Score{" +
                    "winner='" + winner + '\'' +
                    ", duration='" + duration + '\'' +
                    ", fullTime=" + fullTime +
                    ", halfTime=" + halfTime +
                    ", extraTime=" + extraTime +
                    ", penalties=" + penalties +
                    '}';
        }

        class Period {
            int homeTeam;
            int awayTeam;

            @Override
            public String toString() {
                return "Period{" +
                        "homeTeam=" + homeTeam +
                        ", awayTeam=" + awayTeam +
                        '}';
            }
        }
    }

    class Team {
        int id;
        String name;

        @Override
        public String toString() {
            return "Team{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    class Referee {
        int id;
        String name;
        String role;
        String nationality;

        @Override
        public String toString() {
            return "Referee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", role='" + role + '\'' +
                    ", nationality='" + nationality + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", competition=" + competition +
                ", season=" + season +
                ", utcDate='" + utcDate + '\'' +
                ", status='" + status + '\'' +
                ", matchday=" + matchday +
                ", stage='" + stage + '\'' +
                ", group=" + group +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", odds=" + odds +
                ", score=" + score +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", referees=" + Arrays.toString(referees) +
                '}';
    }
}
