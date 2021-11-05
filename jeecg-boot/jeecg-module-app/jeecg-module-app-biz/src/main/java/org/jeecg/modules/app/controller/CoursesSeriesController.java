package org.jeecg.modules.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.app.entity.CoursesDetail;
import org.jeecg.modules.app.entity.CoursesSeries;
import org.jeecg.modules.app.service.ICoursesDetailService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.app.vo.CoursesSeriesPage;
import org.jeecg.modules.app.service.ICoursesSeriesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 系列课程表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Api(tags="系列课程表")
@RestController
@RequestMapping("/app/courses/coursesSeries")
@Slf4j
public class CoursesSeriesController {
	@Autowired
	private ICoursesSeriesService coursesSeriesService;
	@Autowired
	private ICoursesDetailService coursesDetailService;

	/**
	 *   添加
	 *
	 * @param coursesSeriesPage
	 * @return
	 */
	@AutoLog(value = "系列课程表-添加")
	@ApiOperation(value="系列课程表-添加", notes="系列课程表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesSeriesPage coursesSeriesPage) {
		CoursesSeries coursesSeries = new CoursesSeries();
		BeanUtils.copyProperties(coursesSeriesPage, coursesSeries);
		coursesSeriesService.saveMain(coursesSeries, coursesSeriesPage.getCoursesDetailList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coursesSeriesPage
	 * @return
	 */
	@AutoLog(value = "系列课程表-编辑")
	@ApiOperation(value="系列课程表-编辑", notes="系列课程表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoursesSeriesPage coursesSeriesPage) {
		CoursesSeries coursesSeries = new CoursesSeries();
		BeanUtils.copyProperties(coursesSeriesPage, coursesSeries);
		CoursesSeries coursesSeriesEntity = coursesSeriesService.getById(coursesSeries.getId());
		if(coursesSeriesEntity==null) {
			return Result.error("未找到对应数据");
		}
		coursesSeriesService.updateMain(coursesSeries, coursesSeriesPage.getCoursesDetailList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "系列课程表-通过id删除")
	@ApiOperation(value="系列课程表-通过id删除", notes="系列课程表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coursesSeriesService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "系列课程表-批量删除")
	@ApiOperation(value="系列课程表-批量删除", notes="系列课程表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coursesSeriesService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coursesSeries
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoursesSeries coursesSeries) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<CoursesSeries> queryWrapper = QueryGenerator.initQueryWrapper(coursesSeries, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<CoursesSeries> queryList = coursesSeriesService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<CoursesSeries> coursesSeriesList = new ArrayList<CoursesSeries>();
      if(oConvertUtils.isEmpty(selections)) {
          coursesSeriesList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          coursesSeriesList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<CoursesSeriesPage> pageList = new ArrayList<CoursesSeriesPage>();
      for (CoursesSeries main : coursesSeriesList) {
          CoursesSeriesPage vo = new CoursesSeriesPage();
          BeanUtils.copyProperties(main, vo);
          List<CoursesDetail> coursesDetailList = coursesDetailService.selectByMainId(main.getId());
          vo.setCoursesDetailList(coursesDetailList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "系列课程表列表");
      mv.addObject(NormalExcelConstants.CLASS, CoursesSeriesPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("系列课程表数据", "导出人:"+sysUser.getRealname(), "系列课程表"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<CoursesSeriesPage> list = ExcelImportUtil.importExcel(file.getInputStream(), CoursesSeriesPage.class, params);
              for (CoursesSeriesPage page : list) {
                  CoursesSeries po = new CoursesSeries();
                  BeanUtils.copyProperties(page, po);
                  coursesSeriesService.saveMain(po, page.getCoursesDetailList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
