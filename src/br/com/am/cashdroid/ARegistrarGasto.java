package br.com.am.cashdroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.am.cashdroid.control.ControleDados;
import br.com.am.cashdroid.listeners.DateOnClickListener;
import br.com.am.cashdroid.listeners.SairOnClickListener;
import br.com.am.cashdroid.listeners.SalvarOnClickListener;
import br.com.am.cashdroid.listeners.VoltarOnClickListener;
import br.com.am.cashdroid.messages.Mensagem;
import br.com.am.cashdroid.messages.TipoMensagem;
import br.com.am.cashdroid.model.Categoria;
import br.com.am.cashdroid.model.Gasto;
import br.com.am.cashdroid.model.Sessao;
import br.com.am.cashdroid.model.SubCategoria;

public class ARegistrarGasto extends Activity {
	EditText txtData, txtValor;
	Spinner cmbCategoria, cmbSubCategoria;
	Button btnVoltar, btnNovaCat, btnNovaSubCat, btnCadastrar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_registrar_gasto);

		txtData = (EditText) findViewById(R.id.txtData);
		txtData.setOnClickListener(new DateOnClickListener(txtData));
		txtData.setText(DateFormat.format("dd/MM/yyyy", new Date()));

		cmbCategoria = (Spinner) findViewById(R.id.cmbCategoria);
		cmbSubCategoria = (Spinner) findViewById(R.id.cmbSubCategoria);
		cmbCategoria
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {
						getSubCategorias((Categoria) cmbCategoria
								.getSelectedItem());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		cmbSubCategoria
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {
						SubCategoria c = (SubCategoria) cmbSubCategoria
								.getItemAtPosition(position);
						if (c.getId() == 0) {
							final Dialog d = new Dialog(v.getContext());
							d.setContentView(R.layout.m_dialog);
							d.setCancelable(true);

							TextView label = (TextView) d
									.findViewById(R.id.lblDialog);
							label.setText(R.string.dialog_subCategoria);

							Button btnVoltar = (Button) d
									.findViewById(R.id.btnVoltar);
							btnVoltar
									.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											d.cancel();
										}
									});

							Button btnOk = (Button) d.findViewById(R.id.btnOk);
							btnOk
									.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											Categoria cat = (Categoria) cmbCategoria
													.getSelectedItem();
											EditText txt = (EditText) d
													.findViewById(R.id.txtDialog);
											String text = txt.getText()
													.toString();
											if (text.trim().length() != 0) {
												SubCategoria subCategoria = new SubCategoria();
												subCategoria.setDescricao(text);
												subCategoria.setCategoriaId(cat
														.getId());
												subCategoria
														.setUsuarioId(Sessao
																.getUsuarioLogado()
																.getId());

												ControleDados<SubCategoria> cSubCat = new ControleDados<SubCategoria>(
														v.getContext(),
														SubCategoria.class);
												try {
													subCategoria = cSubCat
															.salvar(subCategoria);

													getSubCategorias(cat);

													cmbCategoria
															.setSelection(findSubCategoria(
																	cmbCategoria,
																	subCategoria));
												} catch (Exception e) {
													return;
												}

											}
											d.dismiss();
										}

										private int findSubCategoria(
												Spinner cmbSubCategoria,
												SubCategoria subCategoria) {
											for (int i = 0; i < cmbSubCategoria
													.getCount(); i++) {
												SubCategoria subCat = (SubCategoria) cmbSubCategoria
														.getItemAtPosition(i);
												if (subCat.getUsuarioId() == Sessao
														.getUsuarioLogado()
														.getId()
														&& subCat
																.getCategoriaId() == subCategoria
																.getCategoriaId()
														&& subCat
																.getDescricao()
																.equals(
																		subCategoria
																				.getDescricao()))
													return i;
											}
											return -1;
										}
									});

							d.show();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		btnNovaCat = (Button) findViewById(R.id.btnNovaCategoria);
		btnNovaCat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog d = new Dialog(v.getContext());
				d.setContentView(R.layout.m_dialog);
				d.setCancelable(true);

				TextView label = (TextView) d.findViewById(R.id.lblDialog);
				label.setText(R.string.dialog_categoria);

				Button btnVoltar = (Button) d.findViewById(R.id.btnVoltar);
				btnVoltar.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						d.cancel();
					}
				});

				Button btnOk = (Button) d.findViewById(R.id.btnOk);
				btnOk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText txt = (EditText) d
								.findViewById(R.id.txtDialog);
						String text = txt.getText().toString();
						if (text.trim().length() != 0) {
							Categoria categoria = new Categoria();
							categoria.setDescricao(text);
							categoria.setUsuarioId(Sessao.getUsuarioLogado()
									.getId());

							ControleDados<Categoria> cCategoria = new ControleDados<Categoria>(
									v.getContext(), Categoria.class);
							try {
								cCategoria.salvar(categoria);

								getCategorias();

								cmbCategoria.setSelection(findCategoria(
										cmbCategoria, categoria));
							} catch (Exception e) {
								Mensagem msg = new Mensagem(v.getContext(),
										TipoMensagem.ERRO,
										R.string.erroInesperado);
							}
						}
						d.dismiss();
					}

					private int findCategoria(Spinner cmbCategoria,
							Categoria categoria) {
						for (int i = 0; i < cmbCategoria.getCount(); i++) {
							Categoria c = (Categoria) cmbCategoria
									.getItemAtPosition(i);
							if (c.getUsuarioId() == Sessao.getUsuarioLogado()
									.getId()
									&& c.getDescricao().equals(
											categoria.getDescricao()))
								return i;
						}
						return -1;
					}
				});

				d.show();
			}
		});

		btnNovaSubCat = (Button) findViewById(R.id.btnNovaSubCategoria);
		btnNovaSubCat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Categoria c = (Categoria) cmbCategoria.getSelectedItem();
				if (c != null) {
					final Dialog d1 = new Dialog(v.getContext());
					d1.setContentView(R.layout.m_dialog);
					d1.setCancelable(true);

					TextView label = (TextView) d1.findViewById(R.id.lblDialog);
					label.setText(R.string.dialog_subCategoria);

					Button btnVoltar = (Button) d1.findViewById(R.id.btnVoltar);
					btnVoltar.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							d1.cancel();
						}
					});

					Button btnOk = (Button) d1.findViewById(R.id.btnOk);
					btnOk.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							EditText txt = (EditText) d1
									.findViewById(R.id.txtDialog);
							String text = txt.getText().toString();
							if (text.trim().length() != 0) {
								SubCategoria subCategoria = new SubCategoria();
								subCategoria.setDescricao(text);
								subCategoria.setCategoriaId(c.getId());
								subCategoria.setUsuarioId(Sessao
										.getUsuarioLogado().getId());

								ControleDados<SubCategoria> cSubCat = new ControleDados<SubCategoria>(
										v.getContext(), SubCategoria.class);
								try {
									subCategoria = cSubCat.salvar(subCategoria);

									getSubCategorias(c);

									cmbSubCategoria
											.setSelection(findSubCategoria(
													cmbSubCategoria,
													subCategoria));
								} catch (Exception e) {
									Mensagem msg = new Mensagem(v.getContext(),
											TipoMensagem.ERRO,
											R.string.erroInesperado);
								}
							}
							d1.dismiss();
						}

						private int findSubCategoria(Spinner cmbSubCategoria,
								SubCategoria subCategoria) {
							for (int i = 0; i < cmbSubCategoria.getCount(); i++) {
								SubCategoria subCat = (SubCategoria) cmbSubCategoria
										.getItemAtPosition(i);
								if (subCat.getUsuarioId() == Sessao
										.getUsuarioLogado().getId()
										&& subCat.getCategoriaId() == subCategoria
												.getCategoriaId()
										&& subCat.getDescricao().equals(
												subCategoria.getDescricao()))
									return i;
							}
							return -1;
						}
					});

					d1.show();
				}
			}
		});

		txtValor = (EditText) findViewById(R.id.txtValor);

		btnVoltar = (Button) findViewById(R.id.btnVoltar);
		btnVoltar.setOnClickListener(new VoltarOnClickListener(this));

		btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
		btnCadastrar.setOnClickListener(new SalvarOnClickListener<Gasto>(this) {
			@Override
			public void onClick(View v) {
				Mensagem msg = new Mensagem(v.getContext(), TipoMensagem.AVISO);
				if (cmbCategoria.getSelectedItem() == null) {
					msg
							.setMessage(R.string.mensagem_aviso_categoria_nao_informada);
					msg.show();
					return;
				}
				if (txtValor.getText().toString().trim().length() == 0) {
					msg.setMessage(R.string.mensagem_aviso_valor_nao_informado);
					msg.show();
					return;
				}

				Gasto g = new Gasto();
				g.setCategoriaId(((Categoria) cmbCategoria.getSelectedItem())
						.getId());
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date dt;
				try {
					dt = sdf.parse(txtData.getText().toString());
				} catch (ParseException e) {
					msg.setMessage(R.string.mensagem_aviso_data_invalida);
					msg.show();
					return;
				}
				g.setData( new SimpleDateFormat("yyyy-MM-dd").format(dt)  );
				if (cmbSubCategoria.getSelectedItem() != null)
					g.setSubCategoriaId(((SubCategoria) cmbSubCategoria
							.getSelectedItem()).getId());
				g.setUsuarioId(Sessao.getUsuarioLogado().getId());
				g.setValor(new Double(txtValor.getText().toString()));
				super.t = g;

				super.onClick(v);

				txtValor.setText("");
			}
		});

		/*
		 * obter as categorias.
		 */
		getCategorias();
	}

	private void getSubCategorias(Categoria categoria) {
		if (categoria == null) {
			cmbSubCategoria.setAdapter(null);
			return;
		}

		ControleDados<SubCategoria> controle = new ControleDados<SubCategoria>(
				this, SubCategoria.class);
		try {
			cmbSubCategoria.setAdapter(controle.getArrayAdapterOf(new String[] {
					"categoriaId", "usuarioId" }, new String[] {
					String.format("%s", categoria.getId()),
					String.format("%s", Sessao.getUsuarioLogado().getId()) }));
		} catch (Exception e) {
			return;
		}
	}

	private void getCategorias() {
		ControleDados<Categoria> controle = new ControleDados<Categoria>(this,
				Categoria.class);
		try {
			cmbCategoria.setAdapter(controle.getArrayAdapterOf(
					new String[] { "usuarioId" }, new String[] { String.format(
							"%s", Sessao.getUsuarioLogado().getId()) }));

		} catch (Exception e) {
			return;
		}
	}
}
