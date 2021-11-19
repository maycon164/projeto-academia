package com.academia.factory;

import com.academia.control.AlunoControl;
import com.academia.control.InstrutorControl;
import com.academia.control.PagamentoControl;
import com.academia.control.PlanoControl;

public class ControllerMediator {

	private static AlunoControl alunoControl = null;
	private static PlanoControl planoControl = null;
	private static InstrutorControl instrutorControl = null;
	private static PagamentoControl pagamentoControl = null;

	public static AlunoControl getAlunoControl() {

		if (alunoControl == null) {
			alunoControl = new AlunoControl();

		}

		return alunoControl;

	}

	public static PlanoControl getPlanoControl() {
		if (planoControl == null) {
			planoControl = new PlanoControl();
		}
		return planoControl;
	}

	public static InstrutorControl getInstrutorControl() {

		if (instrutorControl == null) {
			instrutorControl = new InstrutorControl();
		}

		return instrutorControl;
	}

	public static PagamentoControl getPagamentoControl() {
		if (pagamentoControl == null) {
			pagamentoControl = new PagamentoControl();
		}
		return pagamentoControl;
	}

}
