package com.mycom.myapp.board.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;
import com.mycom.myapp.board.service.BoardService;

@Controller
@ResponseBody
@RequestMapping("/boards")
public class BoardController {

	// 생성자 DI
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/list")
	public BoardResultDto listBoard(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = null;
		
		// 검색어 포함여부에 따른 분리처리 Controller -> Service 에서 구현도 가능
		if( Strings.isEmpty(boardParamDto.getSearchWord())) {
			boardResultDto = boardService.listBoard(boardParamDto);
		}else {
			boardResultDto = boardService.listBoardSearchWord(boardParamDto);
		}
		
		return boardResultDto;
	}
}
