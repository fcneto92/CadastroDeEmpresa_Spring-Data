package br.com.mew.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.mew.spring.data.orm.Funcionario;
import br.com.mew.spring.data.orm.FuncionarioProjecao;
import br.com.mew.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
		
	}

	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de cargo deseja executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca funcionário pelo nome");
			System.out.println("2 - Busca funcionário pelo nome, data de contratação e salário maior");
			System.out.println("3 - Busca funcionário pelo nome e data de contratação");
			System.out.println("4 - Pesquisa de funcionário e salário");

			
			int action = scanner.nextInt();
			
			switch (action) {
			case 1:
				buscaFuncionarioNome(scanner);
				break;	
			case 2:
				buscaFuncionarioNomeSalarioMaiorData(scanner);
			case 3:
				buscaFuncionarioDataContratacao(scanner);
				break;
			case 4:
				pesquisafuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}

		}
		
	}
	
	private void buscaFuncionarioNome(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar:");
		String nome = scanner.next();
		List<Funcionario> list = funcionarioRepository.findByNome(nome);
		list.forEach(System.out::println);
		
	}
	
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar:");
		String nome = scanner.next();
		
		System.out.println("Qual data de contratação deseja pesquisar:");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		System.out.println("Qual salário deseja pesquisar:");
		Double salario = scanner.nextDouble();
		
		List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioDataContratacao(Scanner scanner) {
		
		System.out.println("Qual data de contratação deseja pesquisar:");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funcionarioRepository.findaDataContratacaoMaior(localDate);
		list.forEach(System.out::println);
	}
	
	private void pesquisafuncionarioSalario() {
		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("Funcionário: id: " + f.getId() 
		+ " | nome:" + f.getNome() + " | salario:" + f.getSalario()));
	}
	
}
