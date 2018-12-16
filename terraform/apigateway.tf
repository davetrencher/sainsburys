resource "aws_api_gateway_rest_api" "get_products_api" {

  name = "get_products_api"
  description= "Get Products API"
}

resource "aws_api_gateway_api_key" "get_products_api_key" {

  name = "api_key_${aws_api_gateway_rest_api.get_products_api.name}"

}

resource "aws_api_gateway_resource" "get_products_api_gateway_resource" {

  parent_id = "${aws_api_gateway_rest_api.get_products_api.root_resource_id}"
  rest_api_id = "${aws_api_gateway_rest_api.get_products_api.id}"
  path_part = "${var.api_path}"

}

resource "aws_api_gateway_method" "get_products_method" {

  rest_api_id = "${aws_api_gateway_rest_api.get_products_api.id}"
  resource_id = "${aws_api_gateway_resource.get_products_api_gateway_resource.id}"

  http_method = "${var.http_method}"
  authorization = "none"
  api_key_required = true

}

resource "aws_api_gateway_integration" "get_products_integration" {

  http_method = "${aws_api_gateway_method.get_products_method.http_method}"
  resource_id = "${aws_api_gateway_resource.get_products_api_gateway_resource.id}"
  rest_api_id = "${aws_api_gateway_rest_api.get_products_api.id}"
  type = "AWS"
  uri = "arn:aws:apigateway:${var.region}:lambda:path/2015-03-31/functions/${aws_lambda_function.get_product_function.arn}/invocations"
  integration_http_method = "POST"
  credentials = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:role/${aws_iam_role.lambda_apigateway_iam_role.name}"
}

resource "aws_api_gateway_method_response" "200" {

  http_method = "${aws_api_gateway_method.get_products_method.http_method}"
  resource_id = "${aws_api_gateway_resource.get_products_api_gateway_resource.id}"
  rest_api_id = "${aws_api_gateway_rest_api.get_products_api.id}"
  status_code = "200"

  response_models {
    "application/json" = "Empty"
  }
}

resource "aws_api_gateway_integration_response" "get_products_integration_response" {

  depends_on = ["aws_api_gateway_integration.get_products_integration"]
  http_method = "${aws_api_gateway_method.get_products_method.http_method}"
  resource_id = "${aws_api_gateway_resource.get_products_api_gateway_resource.id}"
  rest_api_id = "${aws_api_gateway_rest_api.get_products_api.id}"
  status_code = "${aws_api_gateway_method_response.200.status_code}"
}

resource "aws_api_gateway_deployment" "get_products_deploy" {

  depends_on = ["aws_api_gateway_integration.get_products_integration"]
  rest_api_id = "${aws_api_gateway_rest_api.get_products_api.id}"
  stage_name = "${var.stage_name}"
}

resource "aws_api_gateway_usage_plan" "get_products_usage_plan" {

  name = "usage_plan_${aws_api_gateway_rest_api.get_products_api.name}"

  api_stages {
    api_id = "${aws_api_gateway_rest_api.get_products_api.id}"
    stage = "${aws_api_gateway_deployment.get_products_deploy.stage_name}"
  }

  quota_settings {
    limit = 300
    period = "WEEK"
  }

  throttle_settings {
    burst_limit = 10
    rate_limit = 20
  }
}

resource "aws_api_gateway_usage_plan_key" "get_products_usage_plan_key" {

  key_id = "${aws_api_gateway_api_key.get_products_api_key.id}"
  key_type = "API_KEY"
  usage_plan_id = "${aws_api_gateway_usage_plan.get_products_usage_plan.id}"
}


