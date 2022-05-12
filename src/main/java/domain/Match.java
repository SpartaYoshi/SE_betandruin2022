package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;

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
    final Team homeTeam;
    final Team awayTeam;
    Referee[] referees;

    String parsedDate;

    public Match(String homeTeam, String awayTeam, String parsedDate) {
        this.parsedDate = parsedDate;
        this.homeTeam = new Team(homeTeam);
        this.awayTeam = new Team(awayTeam);
    }

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
        final String name;

        public Team (String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Team{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Team team = (Team) o;
            return Objects.equals(name, team.name);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;

        if (parsedDate == null || parsedDate.equals(""))
            parseDate();

        return Objects.equals(parsedDate, match.parsedDate) && Objects.equals(homeTeam, match.homeTeam) && Objects.equals(awayTeam, match.awayTeam);
    }


    public void parseDate() {
        int splitter = utcDate.indexOf("T");

        if (splitter != -1) {
            parsedDate = utcDate.substring(0, splitter);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                parsedDate = (sdf2.format(sdf.parse(parsedDate)));
            } catch (ParseException e) {
                e.printStackTrace();
                parsedDate = null;
            }
        }
    }

    public String getWinner() {
        return score.winner;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalGoals() {
        return score.fullTime.homeTeam + score.fullTime.awayTeam
                + score.halfTime.homeTeam + score.halfTime.awayTeam
                + score.extraTime.homeTeam + score.extraTime.awayTeam
                + score.penalties.homeTeam + score.penalties.awayTeam;
    }
}
