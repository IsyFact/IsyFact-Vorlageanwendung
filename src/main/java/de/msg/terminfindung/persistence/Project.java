package de.msg.terminfindung.persistence;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Project {
    @EmbeddedId
    ProjectId id;
}

@Embeddable
class ProjectId implements Serializable {
    int departmentId;
    long projectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectId projectId1 = (ProjectId) o;
        return departmentId == projectId1.departmentId && projectId == projectId1.projectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, projectId);
    }
}
