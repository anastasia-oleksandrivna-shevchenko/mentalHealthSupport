package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Diary;
import com.shevchenko.mentalHealthSupport.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    private final Map<Long, Diary> diaryEntries = new HashMap<>();
    private Long nextId = 1L;

    @GetMapping("")
    public String showDiaryPage(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "mood", required = false) String mood,
            Model model) {

        // TODO: У реальному додатку тут має бути перевірка автентифікації користувача

        User currentUser = getDemoUser();
        model.addAttribute("currentUser", currentUser);

        List<Diary> userEntries = getUserEntries(currentUser);

        if (filter != null || mood != null) {
            userEntries = filterEntries(userEntries, filter, mood);
        }

        model.addAttribute("diaryEntries", userEntries);
        return "diary";
    }

    @PostMapping("/add")
    public String addDiaryEntry(
            @RequestParam("content") String content,
            @RequestParam("mood") String mood,
            @RequestParam(value = "tags", required = false) String[] tagsArray,
            @RequestParam(value = "isPrivate", required = false, defaultValue = "false") boolean isPrivate,
            RedirectAttributes redirectAttributes) {

        try {
            // TODO: У реальному додатку тут має бути перевірка автентифікації користувача
            User currentUser = getDemoUser();

            if (content == null || content.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Запис не може бути порожнім");
                return "redirect:/diary";
            }

            if (mood == null || mood.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Виберіть настрій");
                return "redirect:/diary";
            }

            Diary entry = new Diary();
            entry.setId(nextId++);
            entry.setUser(currentUser);
            entry.setContent(content);
            entry.setMood(mood);
            entry.setPrivate(isPrivate);
            entry.setCreatedAt(LocalDateTime.now());
            entry.setUpdatedAt(LocalDateTime.now());

            if (tagsArray != null && tagsArray.length > 0) {
                List<String> tags = Arrays.asList(tagsArray);
                entry.setTags(tags);
            }

            diaryEntries.put(entry.getId(), entry);

            redirectAttributes.addFlashAttribute("successMessage", "Запис додано успішно");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Помилка при створенні запису: " + e.getMessage());
        }

        return "redirect:/diary";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        // TODO: У реальному додатку тут має бути перевірка автентифікації користувача
        User currentUser = getDemoUser();

        Diary entry = diaryEntries.get(id);
        if (entry == null) {
            return "redirect:/diary";
        }

        if (!Objects.equals(entry.getUser().getUsername(), currentUser.getUsername())) {
            return "redirect:/diary";
        }

        model.addAttribute("entry", entry);
        model.addAttribute("currentUser", currentUser);
        return "diary-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateDiaryEntry(
            @PathVariable("id") Long id,
            @RequestParam("content") String content,
            @RequestParam("mood") String mood,
            @RequestParam(value = "tags", required = false) String[] tagsArray,
            @RequestParam(value = "isPrivate", required = false, defaultValue = "false") boolean isPrivate,
            RedirectAttributes redirectAttributes) {

        try {
            // TODO: У реальному додатку тут має бути перевірка автентифікації користувача
            User currentUser = getDemoUser();

            Diary entry = diaryEntries.get(id);
            if (entry == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Запис не знайдено");
                return "redirect:/diary";
            }

            if (!Objects.equals(entry.getUser().getUsername(), currentUser.getUsername())) {
                redirectAttributes.addFlashAttribute("errorMessage", "У вас немає прав для редагування цього запису");
                return "redirect:/diary";
            }

            entry.setContent(content);
            entry.setMood(mood);
            entry.setPrivate(isPrivate);
            entry.setUpdatedAt(LocalDateTime.now());

            if (tagsArray != null && tagsArray.length > 0) {
                List<String> tags = Arrays.asList(tagsArray);
                entry.setTags(tags);
            } else {
                entry.setTags(new ArrayList<>());
            }

            diaryEntries.put(entry.getId(), entry);

            redirectAttributes.addFlashAttribute("successMessage", "Запис оновлено успішно");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Помилка при оновленні запису: " + e.getMessage());
        }

        return "redirect:/diary";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiaryEntry(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {

        try {
            // TODO: У реальному додатку тут має бути перевірка автентифікації користувача
            User currentUser = getDemoUser();

            Diary entry = diaryEntries.get(id);
            if (entry == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Запис не знайдено");
                return "redirect:/diary";
            }

            if (!Objects.equals(entry.getUser().getUsername(), currentUser.getUsername())) {
                redirectAttributes.addFlashAttribute("errorMessage", "У вас немає прав для видалення цього запису");
                return "redirect:/diary";
            }


            diaryEntries.remove(id);

            redirectAttributes.addFlashAttribute("successMessage", "Запис видалено успішно");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Помилка при видаленні запису: " + e.getMessage());
        }

        return "redirect:/diary";
    }

    @GetMapping("/stats")
    public String showMoodStats(Model model) {
        // TODO: У реальному додатку тут має бути перевірка автентифікації користувача
        User currentUser = getDemoUser();

        List<Diary> userEntries = getUserEntries(currentUser);

        Map<String, Integer> moodCounts = new HashMap<>();
        for (Diary entry : userEntries) {
            String mood = entry.getMood();
            moodCounts.put(mood, moodCounts.getOrDefault(mood, 0) + 1);
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("moodCounts", moodCounts);
        model.addAttribute("totalEntries", userEntries.size());

        return "diary-stats";
    }

    // Допоміжні методи


    private User getDemoUser() {
        User user = new User();
        user.setUsername("anastasia");
        user.setEmail("anastasia@example.com");
        user.setRegisteredAt(LocalDateTime.now().minusDays(30));
        user.setAvatar("/images/avatars/default.png");
        user.setBio("Привіт! Я учасник спільноти взаємодопомоги.");
        return user;
    }

    private List<Diary> getUserEntries(User user) {
        // Якщо у нас ще немає записів, створимо декілька для демонстрації
        if (diaryEntries.isEmpty()) {
            createDemoEntries(user);
        }

        return diaryEntries.values().stream()
                .filter(entry -> Objects.equals(entry.getUser().getUsername(), user.getUsername()))
                .sorted(Comparator.comparing(Diary::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    private List<Diary> filterEntries(List<Diary> entries, String timeFilter, String moodFilter) {
        List<Diary> filteredEntries = new ArrayList<>(entries);

        // Фільтрація за часом
        if (timeFilter != null && !timeFilter.equals("all")) {
            LocalDateTime cutoffDate = LocalDateTime.now();
            switch (timeFilter) {
                case "week" -> cutoffDate = cutoffDate.minusWeeks(1);
                case "month" -> cutoffDate = cutoffDate.minusMonths(1);
                case "year" -> cutoffDate = cutoffDate.minusYears(1);
            }

            final LocalDateTime finalCutoffDate = cutoffDate;
            filteredEntries = filteredEntries.stream()
                    .filter(entry -> entry.getCreatedAt().isAfter(finalCutoffDate))
                    .collect(Collectors.toList());
        }

        // Фільтрація за настроєм
        if (moodFilter != null && !moodFilter.equals("all")) {
            filteredEntries = filteredEntries.stream()
                    .filter(entry -> entry.getMood().equals(moodFilter))
                    .collect(Collectors.toList());
        }

        return filteredEntries;
    }

    private void createDemoEntries(User user) {
        // Запис 1
        Diary entry1 = new Diary();
        entry1.setId(nextId++);
        entry1.setUser(user);
        entry1.setContent("Сьогодні був чудовий день! Ходив на прогулянку в парк і зустрів старого друга. Довго говорили про життя і плани на майбутнє.");
        entry1.setMood("great");
        entry1.setPrivate(true);
        entry1.setCreatedAt(LocalDateTime.now().minusDays(1));
        entry1.setUpdatedAt(LocalDateTime.now().minusDays(1));
        entry1.setTags(Arrays.asList("радість", "зустріч", "спілкування"));
        diaryEntries.put(entry1.getId(), entry1);

        // Запис 2
        Diary entry2 = new Diary();
        entry2.setId(nextId++);
        entry2.setUser(user);
        entry2.setContent("Сьогодні відчуваю тривогу через презентацію на роботі. Намагаюся зосередитись на підготовці, але думки постійно розбігаються.");
        entry2.setMood("bad");
        entry2.setPrivate(true);
        entry2.setCreatedAt(LocalDateTime.now().minusDays(3));
        entry2.setUpdatedAt(LocalDateTime.now().minusDays(3));
        entry2.setTags(Arrays.asList("тривога", "робота", "стрес"));
        diaryEntries.put(entry2.getId(), entry2);

        // Запис 3
        Diary entry3 = new Diary();
        entry3.setId(nextId++);
        entry3.setUser(user);
        entry3.setContent("Медитував сьогодні 20 хвилин. Почуваюся спокійно і зосереджено. Це стає хорошою звичкою.");
        entry3.setMood("good");
        entry3.setPrivate(false);
        entry3.setCreatedAt(LocalDateTime.now().minusDays(7));
        entry3.setUpdatedAt(LocalDateTime.now().minusDays(7));
        entry3.setTags(Arrays.asList("медитація", "спокій", "звички"));
        diaryEntries.put(entry3.getId(), entry3);

        // Запис 4
        Diary entry4 = new Diary();
        entry4.setId(nextId++);
        entry4.setUser(user);
        entry4.setContent("Звичайний день. Нічого особливого не сталося. Робота, дім, вечеря.");
        entry4.setMood("neutral");
        entry4.setPrivate(true);
        entry4.setCreatedAt(LocalDateTime.now().minusDays(14));
        entry4.setUpdatedAt(LocalDateTime.now().minusDays(14));
        entry4.setTags(Arrays.asList("рутина"));
        diaryEntries.put(entry4.getId(), entry4);

        // Запис 5
        Diary entry5 = new Diary();
        entry5.setId(nextId++);
        entry5.setUser(user);
        entry5.setContent("День був дуже важким. Конфлікт з колегою забрав багато емоційних сил. Треба навчитися краще встановлювати межі.");
        entry5.setMood("awful");
        entry5.setPrivate(true);
        entry5.setCreatedAt(LocalDateTime.now().minusDays(21));
        entry5.setUpdatedAt(LocalDateTime.now().minusDays(21));
        entry5.setTags(Arrays.asList("конфлікт", "емоційне вигорання", "межі"));
        diaryEntries.put(entry5.getId(), entry5);
    }
}