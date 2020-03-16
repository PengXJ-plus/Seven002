package mx.admin.modules.system.service.mapper;

import mx.admin.mapper.EntityMapper;
import mx.admin.modules.system.domain.Role;
import mx.admin.modules.system.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = {PermissionMapper.class, MenuMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
