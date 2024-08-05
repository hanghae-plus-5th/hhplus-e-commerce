package practice.hhplusecommerce.common.test;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("test")
@Component
public class DatabaseCleanUp implements InitializingBean {

  @PersistenceContext
  private EntityManager entityManager;

  private final List<String> tables = new ArrayList<>();

  public DatabaseCleanUp(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void afterPropertiesSet() {
    tables.addAll(entityManager.getMetamodel().getEntities().stream()
        .filter(entityType -> entityType.getJavaType().isAnnotationPresent(Entity.class))
        .map(entityType -> {
          Table table = entityType.getJavaType().getAnnotation(Table.class);
          if (table != null && !table.name().isEmpty()) {
            return table.name();
          } else {
            return entityType.getName();
          }
        })
        .collect(Collectors.toList()));
  }

  @Transactional
  public void execute() {
    entityManager.flush();
    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
    for (String table : tables) {

      entityManager.createNativeQuery("TRUNCATE TABLE " + toSnakeCase(table).toLowerCase()).executeUpdate();
      entityManager.createNativeQuery("ALTER TABLE " + toSnakeCase(table).toLowerCase() + " AUTO_INCREMENT = 1").executeUpdate();
    }
    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
  }

  private String toSnakeCase(String str) {
    StringBuilder result = new StringBuilder();
    result.append(Character.toLowerCase(str.charAt(0)));
    for (int i = 1; i < str.length(); i++) {
      char c = str.charAt(i);
      if (Character.isUpperCase(c)) {
        result.append('_').append(Character.toLowerCase(c));
      } else {
        result.append(c);
      }
    }
    return result.toString();
  }
}
