package ru.pvndpl.repository

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.pvndpl.entity.Profile
import java.util.*

private val logger = KotlinLogging.logger {}

@Slf4j
@Repository
class ProfileRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun getUserProfile(userId: UUID): Profile? {

        return jdbcTemplate.queryForObject(
            "SELECT user_id,\n" +
                    "       (SELECT count(*)\n" +
                    "        FROM posts\n" +
                    "        WHERE user_id = '$userId') as posts_count,\n" +
                    "       (SELECT count(*)\n" +
                    "        FROM users_subscribers\n" +
                    "        WHERE user_id = '$userId') as subscribers_count,\n" +
                    "       (SELECT count(*)\n" +
                    "        FROM users_subscribers\n" +
                    "        WHERE subscribers_id = '$userId') as subscriptions_count,\n" +
                    "        about, created_date, city, website, tv_shows, movies, showmen,\n" +
                    "        books, games, email, birthdate, busyness, native_city\n" +
                    "FROM users_profiles\n" +
                    "WHERE user_id = '$userId'\n" +
                    "LIMIT 1",
            ROW_MAPPER_USERS_PROFILES
        )
    }

    fun createUserProfile(userId: UUID, createdDate: Date, email: String) {

        val query = "INSERT INTO users_profiles(user_id, created_date, email) " +
                "VALUES (\'$userId', \'$createdDate', '$email')"

        logger.warn { query }

        jdbcTemplate.update(query)
    }

    fun editAbout(userId: UUID, about: String?, city: String?, website: String?) {

        val query = "UPDATE users_profiles\n" +
                "SET about   = \'$about',\n" +
                "    city    = \'$city',\n" +
                "    website = \'$website'\n" +
                "WHERE user_id = \'$userId'"

        logger.warn { query }

        jdbcTemplate.update(query)
    }

    fun editInterests(userId: UUID, tvShows: String?, showmen: String?, movies: String?, books: String?, games: String?) {

        val query = "UPDATE users_profiles\n" +
                "SET tv_shows = \'$tvShows',\n" +
                "    showmen  = \'$showmen',\n" +
                "    movies  = \'$movies',\n" +
                "    books    = \'$books',\n" +
                "    games    = \'$games'\n" +
                "WHERE user_id = \'$userId'"

        logger.warn { query }

        jdbcTemplate.update(query)
    }

    fun editPersonalInf(userId: UUID, email: String?, birthdate: Date?, busyness: String?, nativeCity: String?) {

        val query: String

        if (birthdate != null) {
            query = "UPDATE users_profiles\n" +
                    "SET email       = \'$email',\n" +
                    "    birthdate   = \'$birthdate',\n" +
                    "    busyness    = \'$busyness',\n" +
                    "    native_city = \'$nativeCity'\n" +
                    "WHERE user_id = \'$userId'"
        } else {
            query = "UPDATE users_profiles\n" +
                    "SET email       = \'$email',\n" +
                    "    birthdate   = NULL,\n" +
                    "    busyness    = \'$busyness',\n" +
                    "    native_city = \'$nativeCity'\n" +
                    "WHERE user_id = \'$userId'"
        }

        logger.warn { query }

        jdbcTemplate.update(query)
    }

    fun addSocialNetwork(userId: UUID, socialId: Int, url: String) {

        val query = "INSERT INTO profiles_socials_networks(profile_id, social_id, url)\n" +
                "VALUES (\'$userId', \'$socialId', \'$url')"

        logger.warn { query }

        jdbcTemplate.update(query)

    }

    fun removeSocialNetwork(userId: UUID, socialId: Int) {

        val query = "DELETE FROM profiles_socials_networks\n" +
                "        WHERE profile_id = \'$userId' AND social_id = \'$socialId'"

        logger.warn { query }

        jdbcTemplate.update(query)

    }

    fun editSocialNetwork(userId: UUID, socialId: Int, url: String) {

        val query = "UPDATE profiles_socials_networks\n" +
                "                SET url = \'$url'\n" +
                "        WHERE profile_id = \'$userId'\n" +
                "        AND social_id = \'$socialId';"

        logger.warn { query }

        jdbcTemplate.update(query)

    }

    //admin
    fun createSocialNetwork(title: String) {

        val query = "INSERT INTO social_networks(title)\n" +
                "VALUES (\'$title')"

        logger.warn { query }

        jdbcTemplate.update(query)
    }

    private companion object {
        val ROW_MAPPER_USERS_PROFILES = RowMapper<Profile> { rs, _ ->
            Profile(
                rs.getObject("user_id", UUID::class.java),
                rs.getInt("posts_count"),
                rs.getInt("subscribers_count"),
                rs.getInt("subscriptions_count"),
                rs.getString("about"),
                rs.getDate("created_date"),
                rs.getString("city"),
                rs.getString("website"),
                rs.getString("tv_shows"),
                rs.getString("movies"),
                rs.getString("showmen"),
                rs.getString("books"),
                rs.getString("games"),
                rs.getString("email"),
                rs.getDate("birthdate"),
                rs.getString("busyness"),
                rs.getString("native_city"),
            )
        }
    }
}