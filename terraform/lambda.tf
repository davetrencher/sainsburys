resource "aws_lambda_function" "get_product_function" {

  function_name     = "get_product_function"
  filename          = "${var.lambda_payload_filename}"

  role              = "${aws_iam_role.lambda_apigateway_iam_role.arn}"
  handler           = "${var.lambda_function_handler}"
  source_code_hash  = "${base64sha256(file(var.lambda_payload_filename))}"
  runtime           = "${var.lambda_runtime}"
  timeout           = 20
  memory_size       = 256

}

data "aws_caller_identity" "current" {}

resource "aws_lambda_permission" "get_product_permission" {

  function_name     = "arn:aws:lambda:${var.region}:${data.aws_caller_identity.current.account_id}:function:get_product_function"
  statement_id      = "AllowExecutionFromApiGateway"

  action            = "lambda:InvokeFunction"

  principal         = "apigateway.amazonaws.com"
  source_arn        = "arn:aws:execute-api:${var.region}:${data.aws_caller_identity.current.account_id}:${aws_api_gateway_rest_api.get_products_api.id}/${aws_api_gateway_deployment.get_products_deploy.stage_name}/${aws_api_gateway_integration.get_products_integration.http_method}${aws_api_gateway_resource.get_products_api_gateway_resource.path}"
}

