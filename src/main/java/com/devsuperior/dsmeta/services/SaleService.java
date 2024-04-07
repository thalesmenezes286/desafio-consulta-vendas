package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService 
{

	@Autowired
	private SaleRepository repository;

	private LocalDate minhaData = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

	public SaleMinDTO findById(Long id) 
	{
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> findReports(String minDate, String maxDate, String name, Pageable pageable) 
	{

		LocalDate result = minDate.equals("") ? minhaData.minusYears(1L) : LocalDate.parse(minDate);
		LocalDate today = maxDate.equals("") ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate);

		return repository.searchReport(result, today, name, pageable);
	}

	public Page<SaleSummaryDTO> findSummaries(String minDate, String maxDate, Pageable pageable) 
	{

		LocalDate result = minDate.equals("") ? minhaData.minusYears(1L) : LocalDate.parse(minDate);
		LocalDate today = maxDate.equals("") ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate);
		
		return repository.searchSummary(result, today, pageable);
	}
}
